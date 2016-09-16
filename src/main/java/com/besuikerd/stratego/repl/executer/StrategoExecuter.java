package com.besuikerd.stratego.repl.executer;

import com.besuikerd.mainrunner.ClassFunctionException;
import com.besuikerd.mainrunner.ExecutionException;
import com.besuikerd.mainrunner.MainRunner;
import com.besuikerd.stratego.repl.compiler.ICompilationPath;
import com.besuikerd.stratego.repl.rule.ICompiledStrategoRule;
import com.besuikerd.stratego.repl.term.IStrategoTerm;
import com.besuikerd.stratego.repl.term.StrategoTerm;
import org.strategoxt.lang.StrategoExit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;

@Singleton
@Named
public class StrategoExecuter implements IStrategoExecuter{

    private MainRunner runner;
    private ICompilationPath compilationPath;

    @Inject
    public StrategoExecuter(ICompilationPath compilationPath) throws MalformedURLException {
        this.compilationPath = compilationPath;
        runner = MainRunner.getInstance();
        runner.addToClassPath(compilationPath.getPath().toFile().getAbsolutePath());
    }

    @Override
    public IStrategoTerm execute(ICompiledStrategoRule rule) throws StrategoExecutionException {
        PrintStream systemOut = System.out;
        PrintStream systemErr = System.err;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bos));
            System.setErr(new PrintStream(bos));
            runner.tryMain(rule.getIdentity());
            return new StrategoTerm(rule, new String(bos.toByteArray()));
        } catch (ExecutionException|ClassFunctionException e) {
            try{
                throw e.getCause();
            } catch(StrategoExit exit){
                if(exit.getValue() != 0){
                    String message = exit.getMessage().startsWith("Legal exit") ? new String(bos.toByteArray()) : exit.getMessage();
                    throw new StrategoExecutionException(message);
                } else{
                    String term = new String(bos.toByteArray()).trim();
                    return new StrategoTerm(rule, term);
                }
            } catch(Throwable e2){
                throw new StrategoExecutionException(e2.getMessage());
            }
        } finally{
            System.setOut(systemOut);
            System.setErr(systemErr);
        }
    }
}
