package com.besuikerd.stratego.repl.shell.commands;

import com.besuikerd.stratego.repl.history.ITermHistory;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class UndoCommand implements CommandMarker {

    @Inject
    ITermHistory history;

    @CliCommand(value = {":undo", ":u"}, help = "undo a certain number of strategies")
    public String undo(@CliOption(key = "", mandatory = false, unspecifiedDefaultValue = "1") final int n){
        history.undo(n);
        return history.hasTerms() ?
            "Last term is now\n" + history.last().getStringRepresentation() :
            "history is now empty";
    }
}
