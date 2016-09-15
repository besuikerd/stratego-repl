package com.besuikerd.stratego.repl.executer;

import com.besuikerd.stratego.repl.rule.ICompiledStrategoRule;
import com.besuikerd.stratego.repl.term.IStrategoTerm;

public interface IStrategoExecuter {
    public IStrategoTerm execute(ICompiledStrategoRule rule) throws StrategoExecutionException;
}
