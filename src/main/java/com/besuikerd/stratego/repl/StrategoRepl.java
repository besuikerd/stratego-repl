package com.besuikerd.stratego.repl;

import com.besuikerd.stratego.repl.compiler.CompilationException;
import com.besuikerd.stratego.repl.compiler.IStrategoCompiler;
import com.besuikerd.stratego.repl.executer.IStrategoExecuter;
import com.besuikerd.stratego.repl.executer.StrategoExecutionException;
import com.besuikerd.stratego.repl.history.ITermHistory;
import com.besuikerd.stratego.repl.rule.ICompiledStrategoRule;
import com.besuikerd.stratego.repl.rule.IStrategoRule;
import com.besuikerd.stratego.repl.rule.StrategoRule;
import com.besuikerd.stratego.repl.term.IStrategoTerm;

import java.io.*;
import java.util.Scanner;

import javax.inject.Inject;
import javax.tools.JavaCompiler;


public class StrategoRepl implements IStrategoRepl {

    private IStrategoCompiler compiler;
    private IStrategoExecuter executer;
    private ITermHistory history;
    private int termIdentity;

    @Inject
    public StrategoRepl(IStrategoCompiler compiler, IStrategoExecuter executer, ITermHistory history) {
        this.compiler = compiler;
        this.executer = executer;
        this.history = history;
        termIdentity = 0;
    }

    public void repl() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Stratego> ");
        IStrategoRule rule = new StrategoRule(scanner.nextLine(), "term_" + termIdentity++);
        try {
            ICompiledStrategoRule compiledRule = compiler.compile(rule);
            IStrategoTerm term = executer.execute(compiledRule);
            System.out.println(term.getStringRepresentation());
            history.store(term);
        } catch (CompilationException e) {
            e.printStackTrace();
        } catch (StrategoExecutionException e) {
            e.printStackTrace();
        }
    }

//            String moduleName = "term_" + term++;
//
//            StrategoTemplateContext templateContext = new StrategoTemplateContext(config.getImports(), rule, moduleName);
//
//            File src = new File(path.toFile(), moduleName + ".str");
//            Writer writer = new OutputStreamWriter(new FileOutputStream(src));
//            template.execute(writer, templateContext);
//            writer.close();
//
//            try{
//                invoke(src);
//            } catch(ExecutionException e){
//                try{
//                    throw e.getCause();
//                } catch(StrategoExit e2){
//                    if(e2.getValue() == 0){
//                        System.out.println("Great success!");
//                    } else{
//                        System.out.println("Great failure!");
//                    }
//                } catch(Throwable e2){
//                    e.printStackTrace();
//                }
//            } catch(StrategoExit e){
//                System.out.println("Compilation error");
//            }
//        }
//    }
}