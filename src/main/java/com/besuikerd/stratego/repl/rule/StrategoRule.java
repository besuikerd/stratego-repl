package com.besuikerd.stratego.repl.rule;

public class StrategoRule implements IStrategoRule{
    private String stringRepresentation;
    private String identity;

    public StrategoRule(String stringRepresentation, String identity) {
        this.stringRepresentation = stringRepresentation;
        this.identity = identity;
    }

    @Override
    public String getStringRepresentation() {
        return stringRepresentation;
    }

    @Override
    public String getIdentity() {
        return identity;
    }
}
