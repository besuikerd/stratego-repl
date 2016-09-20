package com.besuikerd.stratego.repl.shell.commands;

import com.besuikerd.stratego.repl.IStrategoRepl;
import com.besuikerd.stratego.repl.compiler.IStrategoCompiler;
import com.besuikerd.stratego.repl.executer.IStrategoExecuter;
import com.besuikerd.stratego.repl.history.ITermHistory;
import com.besuikerd.stratego.repl.rule.IStrategoRuleFactory;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.ExecutionProcessor;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.event.ParseResult;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class EvalCommand implements CommandMarker{

    private IStrategoRepl repl;
    private IStrategoRuleFactory ruleFactory;

    @Inject
    public EvalCommand(IStrategoRepl repl, IStrategoRuleFactory ruleFactory) {
        this.repl = repl;
        this.ruleFactory = ruleFactory;
    }

    @CliCommand(value=":eval", help="evaluates a stratego strategy on the current term")
    public String eval(@CliOption(key = "", mandatory = true) final String input){
        return repl.repl(ruleFactory.createStrategoRule(input));
    }
}