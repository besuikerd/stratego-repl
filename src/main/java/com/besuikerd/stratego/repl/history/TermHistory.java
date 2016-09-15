package com.besuikerd.stratego.repl.history;

import com.besuikerd.stratego.repl.term.IStrategoTerm;
import com.besuikerd.stratego.repl.term.StrategoTerm;

import java.util.Stack;

public class TermHistory implements ITermHistory{

    private Stack<IStrategoTerm> history;

    public TermHistory(){
        this.history = new Stack<>();
    }

    @Override
    public void store(IStrategoTerm term) {
        history.push(term);
    }

    @Override
    public IStrategoTerm last() {
        return history.peek();
    }

    @Override
    public boolean hasTerms() {
        return !history.empty();
    }
}
