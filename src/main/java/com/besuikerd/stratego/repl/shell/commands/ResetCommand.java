package com.besuikerd.stratego.repl.shell.commands;

import com.besuikerd.stratego.repl.history.ITermHistory;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ResetCommand implements CommandMarker {

    private ITermHistory history;

    @Inject
    public ResetCommand(ITermHistory history) {
        this.history = history;
    }

    @CliCommand(value = ":reset", help = "remove all previous strategies")
    public String reset(){
        history.clear();
        return "history is now empty";
    }
}
