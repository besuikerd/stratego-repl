package com.besuikerd.stratego.repl.compiler;

import com.besuikerd.stratego.repl.history.ITermHistory;
import com.besuikerd.stratego.repl.rule.CompiledStrategoRule;
import com.besuikerd.stratego.repl.rule.ICompiledStrategoRule;
import com.besuikerd.stratego.repl.rule.IStrategoRule;
import com.besuikerd.stratego.repl.rule.StrategoRule;
import com.besuikerd.stratego.repl.template.IStrategoTemplate;
import com.besuikerd.stratego.repl.term.IStrategoTerm;
import org.spoofax.interpreter.library.IOAgent;
import org.strategoxt.lang.Context;
import org.strategoxt.lang.StrategoExit;
import org.strategoxt.strj.main_strj_0_0;

import javax.inject.Inject;
import javax.tools.JavaCompiler;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;

public class StrategoToJBCCompiler implements IStrategoCompiler {
    private ICompilationPath compilationPath;
    private JavaCompiler compiler;
    private ITermHistory history;
    private IStrategoTemplate template;
    private Context context;


    private InputStream nullInput = new ByteArrayInputStream(new byte[0]);
    private OutputStream nullOutput = new OutputStream() {
        @Override
        public void write(int b) throws IOException {

        }
    };

    @Inject
    public StrategoToJBCCompiler(ICompilationPath compilationPath, JavaCompiler compiler, ITermHistory history, IStrategoTemplate template, Context context) {
        this.compilationPath = compilationPath;
        this.compiler = compiler;
        this.history = history;
        this.template = template;
        this.context = context;
        makeStdErrWriterNonFinal();
        makeStdOutWriterNonFinal();
    }

    @Override
    public ICompiledStrategoRule compile(IStrategoRule rule) throws CompilationException {
        rule = prependHistory(rule);
        writeStrategoFile(rule);
        compileStratego(rule);
        replaceExitClauses(rule);
        compileJava(rule);
        return new CompiledStrategoRule(rule);
    }

    private IStrategoRule prependHistory(IStrategoRule rule){
        String prefix = history.hasTerms() ? "!" + history.last().getStringRepresentation() + " ; " : "";
        return new StrategoRule(prefix + rule.getStringRepresentation(), rule.getIdentity());
    }

    private void writeStrategoFile(IStrategoRule rule) throws CompilationException {
        File destination = new File(compilationPath.getPath().toFile(), rule.getIdentity() + ".str");
        try(
            BufferedInputStream is = new BufferedInputStream(template.writeTemplate(rule));
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(destination));
        ) {
            byte[] buff = new byte[1024];
            int n = -1;
            while((n = is.read(buff)) != -1){
                os.write(buff, 0, n);
            }
            is.close();
            os.close();
        } catch(IOException e){
            throw new CompilationException("Could not compile: " + e.getMessage());
        }
    }

    private void makeFieldNonFinal(Object obj, String name){
        Class<?> cls = obj.getClass();
        try {
            Field f = cls.getDeclaredField(name);
            f.setAccessible(true);
            Field fmodifiers = Field.class.getDeclaredField("modifiers");
            fmodifiers.setAccessible(true);
            fmodifiers.setInt(f, f.getModifiers() & ~Modifier.FINAL);
        } catch (IllegalAccessException|NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void makeStdErrWriterNonFinal(){
        makeFieldNonFinal(context.getIOAgent(), "stderrWriter");
    }

    private void makeStdOutWriterNonFinal(){
        makeFieldNonFinal(context.getIOAgent(), "stdoutWriter");
    }

    private Writer replaceWriter(String field, Writer replacement){
        IOAgent agent = context.getIOAgent();
        Class<?> cls = agent.getClass();
        try{
            Field f =  cls.getDeclaredField(field);
            f.setAccessible(true);
            Writer stdErr = (Writer) f.get(agent);
            f.set(agent, replacement);
            return stdErr;
        } catch (IllegalAccessException|NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Writer replaceStdErrWriter(Writer replacement){
        return replaceWriter("stderrWriter", replacement);
    }

    private Writer replaceStdOutWriter(Writer replacement){
        return replaceWriter("stdoutWriter", replacement);
    }

    private void compileStratego(IStrategoRule rule) throws CompilationException {
        File src = new File(compilationPath.getPath().toFile(), rule.getIdentity() + ".str");
        String[] args = new String[]{"-i", src.getAbsolutePath()};

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(bos);
        Writer stdErr = replaceStdErrWriter(writer);
        Writer stdOut = replaceStdOutWriter(writer);
        try{
            context.invokeStrategyCLI(main_strj_0_0.instance, "Main", args);
        } catch(StrategoExit e){
            if(e.getValue() != 0){
                String message = e.getMessage().startsWith("Legal exit") ? new String(bos.toByteArray()) : e.getMessage();
                throw new CompilationException(message);
            }
        } finally{
            replaceStdErrWriter(stdErr);
            replaceStdOutWriter(stdOut);
            context.getIOAgent().closeAllFiles();
        }
    }

    private void replaceExitClauses(IStrategoRule rule) throws CompilationException {
        File src = new File(compilationPath.getPath().toFile(), rule.getIdentity() + ".java");
        try {
            String javaSourceString = new String(Files.readAllBytes(src.toPath()));
            String replaced = javaSourceString.replaceAll("\\s*System.exit\\((.*)\\);", "throw new StrategoExit($1);");
            Files.write(src.toPath(), replaced.getBytes());
        } catch(IOException e){
            throw new CompilationException(e.getMessage());
        }
    }

    private void compileJava(IStrategoRule rule) throws CompilationException {
        File src = new File(compilationPath.getPath().toFile(), rule.getIdentity() + ".java");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int exitCode = compiler.run(nullInput, nullOutput, bos, "-cp", compilationPath.getClassPath(), src.getAbsolutePath());
        if(exitCode != 0){
            throw new CompilationException(new String(bos.toByteArray()));
        }
    }
}
