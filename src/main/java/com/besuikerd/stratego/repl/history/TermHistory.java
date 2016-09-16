package com.besuikerd.stratego.repl.history;

import com.besuikerd.stratego.repl.rule.IStrategoRule;
import com.besuikerd.stratego.repl.rule.StrategoRule;
import com.besuikerd.stratego.repl.term.IStrategoTerm;
import com.besuikerd.stratego.repl.term.StrategoTerm;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Stack;
import java.util.stream.Collectors;

@Named
@Singleton
public class TermHistory implements ITermHistory{

    private Stack<IStrategoTerm> history;
    private Stack<IStrategoTerm> future;

    public TermHistory(){
        this.history = new Stack<>();
        this.future = new Stack<>();
    }

    @Override
    public void store(IStrategoTerm term) {
        history.push(term);
        future.clear();
    }

    @Override
    public void clear() {
        history.clear();
    }

    @Override
    public void undo(int n) {
        int limit = Math.min(history.size(), n);
        for(int i = 0 ; i < limit ; i++){
            future.push(history.pop());
        }
    }

    @Override
    public void undo() {
        undo(1);
    }

    @Override
    public void redo(int n) {
        int limit = Math.min(future.size(), n);
        for(int i = 0 ; i < limit ; i++){
            history.push(future.pop());
        }
    }

    @Override
    public void redo() {
        redo(1);
    }

    @Override
    public IStrategoRule createHistoryAwareRule(IStrategoRule rule) {
        if(history.isEmpty()){
            return rule;
        }
        String joined = history.peek().getRule().getStringRepresentation() + " ; " + rule.getStringRepresentation();
        return new StrategoRule(joined, rule.getIdentity());
    }

    @Override
    public IStrategoTerm last() {
        return history.peek();
    }

    @Override
    public boolean hasTerms() {
        return !history.isEmpty();
    }
}
