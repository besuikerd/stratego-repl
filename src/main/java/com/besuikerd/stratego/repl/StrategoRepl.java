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
import javax.inject.Named;
import javax.inject.Singleton;
import javax.tools.JavaCompiler;

@Named
@Singleton
public class StrategoRepl implements IStrategoRepl {

    private IStrategoCompiler compiler;
    private IStrategoExecuter executer;
    private ITermHistory history;

    @Inject
    public StrategoRepl(IStrategoCompiler compiler, IStrategoExecuter executer, ITermHistory history) {
        this.compiler = compiler;
        this.executer = executer;
        this.history = history;
    }

    public String repl(IStrategoRule rule) {
        String result = null;
        try {
            ICompiledStrategoRule compiledRule = compiler.compile(rule);
            IStrategoTerm term = executer.execute(compiledRule);
            result = term.getStringRepresentation();
            history.store(term);
        } catch (CompilationException|StrategoExecutionException e) {
            result = e.getMessage();
        }
        return result;
    }
}