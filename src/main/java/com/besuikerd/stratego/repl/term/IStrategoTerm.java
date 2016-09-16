package com.besuikerd.stratego.repl.term;

import com.besuikerd.stratego.repl.rule.IStrategoRule;

public interface IStrategoTerm {
    public IStrategoRule getRule();
    public String getStringRepresentation();
}
