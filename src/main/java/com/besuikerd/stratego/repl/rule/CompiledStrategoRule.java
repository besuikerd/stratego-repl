package com.besuikerd.stratego.repl.rule;

public class CompiledStrategoRule extends StrategoRule implements ICompiledStrategoRule{
    public CompiledStrategoRule(String stringRepresentation, String identity) {
        super(stringRepresentation, identity);
    }

    public CompiledStrategoRule(IStrategoRule rule){
        this(rule.getStringRepresentation(), rule.getIdentity());
    }
}
