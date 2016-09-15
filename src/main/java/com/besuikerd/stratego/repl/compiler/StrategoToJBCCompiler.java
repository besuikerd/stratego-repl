package com.besuikerd.stratego.repl.compiler;

import com.besuikerd.stratego.repl.history.ITermHistory;
import com.besuikerd.stratego.repl.rule.CompiledStrategoRule;
import com.besuikerd.stratego.repl.rule.ICompiledStrategoRule;
import com.besuikerd.stratego.repl.rule.IStrategoRule;
import com.besuikerd.stratego.repl.template.IStrategoTemplate;
import org.strategoxt.lang.Context;
import org.strategoxt.lang.StrategoExit;
import org.strategoxt.strj.main_strj_0_0;

import javax.inject.Inject;
import javax.tools.JavaCompiler;
import java.io.*;
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
    }

    @Override
    public ICompiledStrategoRule compile(IStrategoRule rule) throws CompilationException {
        writeStrategoFile(rule);
        compileStratego(rule);
        replaceExitClauses(rule);
        compileJava(rule);
        return new CompiledStrategoRule(rule);
    }

    private void writeStrategoFile(IStrategoRule rule) throws CompilationException {
        File destination = new File(compilationPath.getPath().toFile(), rule.getIdentity() + ".str");
        try {
            BufferedInputStream is = new BufferedInputStream(template.writeTemplate(rule));
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(destination));
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

    private void compileStratego(IStrategoRule rule) throws CompilationException {
        File src = new File(compilationPath.getPath().toFile(), rule.getIdentity() + ".str");
        String[] args = new String[]{"-i", src.getAbsolutePath()};
        try{
            context.invokeStrategyCLI(main_strj_0_0.instance, "Main", args);
        } catch(StrategoExit e){
            if(e.getValue() != 0){
                throw new CompilationException(e.getMessage());
            }
        } finally{
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
