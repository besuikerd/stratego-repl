package com.besuikerd.stratego.repl.shell.commands;

import com.besuikerd.stratego.repl.history.ITermHistory;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class RedoCommand implements CommandMarker {

    @Inject
    private ITermHistory history;

    @CliCommand(value = {":r", ":redo"}, help = "redo a certain number of strategies")
    public String redo(@CliOption(key = "", mandatory = false, unspecifiedDefaultValue = "1") int n){
        history.redo(n);
        return history.hasTerms() ?
            "Last term is now\n" + history.last().getStringRepresentation() :
            "There is nothing to redo" ;
    }
}
