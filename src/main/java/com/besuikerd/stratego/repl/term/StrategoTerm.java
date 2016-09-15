package com.besuikerd.stratego.repl.term;

public class StrategoTerm implements IStrategoTerm{
    private String stringRepresentation;

    public StrategoTerm(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String getStringRepresentation() {
        return stringRepresentation;
    }
}
