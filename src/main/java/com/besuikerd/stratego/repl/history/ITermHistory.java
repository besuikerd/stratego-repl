package com.besuikerd.stratego.repl.history;


import com.besuikerd.stratego.repl.rule.IStrategoRule;
import com.besuikerd.stratego.repl.term.IStrategoTerm;

public interface ITermHistory {
    public void store(IStrategoTerm term);
    public void clear();
    public void undo(int n);
    public void undo();

    public void redo(int n);
    public void redo();

    public IStrategoRule createHistoryAwareRule(IStrategoRule rule);
    public IStrategoTerm last();
    public boolean hasTerms();
}