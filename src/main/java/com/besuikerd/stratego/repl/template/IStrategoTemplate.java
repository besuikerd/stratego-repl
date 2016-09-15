package com.besuikerd.stratego.repl.template;

import com.besuikerd.stratego.repl.rule.IStrategoRule;

import java.io.InputStream;

public interface IStrategoTemplate {
    public InputStream writeTemplate(IStrategoRule rule);
}