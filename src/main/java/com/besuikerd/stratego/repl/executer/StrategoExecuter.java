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
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;

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
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bos));
            runner.tryMain(rule.getIdentity());
            return new StrategoTerm(new String(bos.toByteArray()));
        } catch (ExecutionException|ClassFunctionException e) {
            try{
                throw e.getCause();
            } catch(StrategoExit exit){
                if(exit.getValue() != 0){
                    throw new StrategoExecutionException(exit.getMessage());
                } else{
                    String term = new String(bos.toByteArray());
                    return new StrategoTerm(term);
                }
            } catch(Throwable e2){
                throw new StrategoExecutionException(e2.getMessage());
            }
        } finally{
            System.setOut(systemOut);
        }
    }

//    private void executeJava(File src) throws ExecutionException {
//        String targetCls = src.getName().replaceAll("\\.str$", "");
//        System.out.println(targetCls);
//        try {
//            runner.tryMain(targetCls);
//        } catch (ExecutionException e) {
//            throw e;
//        } catch (ClassFunctionException e) {
//            e.printStackTrace();
//        }
//    }
}
