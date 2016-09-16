package com.besuikerd.stratego.repl.shell.commands;

import com.besuikerd.stratego.repl.history.ITermHistory;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class InfoCommand implements CommandMarker {
    @Inject
    private ITermHistory history;

    @CliCommand(value = {":i", ":info"}, help = "Displays the strategy that has been built up thus far")
    public String info(){
        return history.hasTerms() ?
                history.last().getRule().getStringRepresentation().replaceAll(" ; ", ";\n"):
                "no strategy has been applied so far";
    }
}
