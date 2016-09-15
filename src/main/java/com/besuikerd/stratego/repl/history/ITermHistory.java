package com.besuikerd.stratego.repl.history;


import com.besuikerd.stratego.repl.term.IStrategoTerm;

public interface ITermHistory {
    public void store(IStrategoTerm term);

    public boolean hasTerms();
    public IStrategoTerm last();
}
