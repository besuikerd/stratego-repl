package com.besuikerd.stratego.repl.shell.commands;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements CommandMarker{
    @CliCommand(value={":q", ":exit"}, help="Exits the shell")
    public ExitShellRequest quit() {
        return ExitShellRequest.NORMAL_EXIT;
    }
}
