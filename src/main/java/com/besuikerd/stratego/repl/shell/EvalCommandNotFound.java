package com.besuikerd.stratego.repl.shell;

import com.besuikerd.stratego.repl.shell.commands.EvalCommand;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class EvalCommandNotFound implements ICommandNotFound{

    @Inject
    private EvalCommand eval;

    @Override
    public void fallback(String command) {
        System.out.println(eval.eval(command));
    }
}
