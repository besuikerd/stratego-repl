package com.besuikerd.stratego.repl.compiler;

import com.besuikerd.stratego.repl.rule.ICompiledStrategoRule;
import com.besuikerd.stratego.repl.rule.IStrategoRule;

public interface IStrategoCompiler {
    public ICompiledStrategoRule compile(IStrategoRule rule) throws CompilationException;
}
