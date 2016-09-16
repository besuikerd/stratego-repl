package com.besuikerd.stratego.repl.rule;

import javax.inject.Named;

@Named
public class StrategoRuleFactory implements IStrategoRuleFactory{
    private int identity;

    @Override
    public IStrategoRule createStrategoRule(String rule) {
        return new StrategoRule(rule, "Term_" + identity++);
    }
}
