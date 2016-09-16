package com.besuikerd.stratego.repl.term;

import com.besuikerd.stratego.repl.rule.IStrategoRule;

public class StrategoTerm implements IStrategoTerm{

    private IStrategoRule rule;
    private String stringRepresentation;

    public StrategoTerm(IStrategoRule rule, String stringRepresentation) {
        this.rule = rule;
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public IStrategoRule getRule() {
        return rule;
    }

    @Override
    public String getStringRepresentation() {
        return stringRepresentation;
    }
}
