package com.besuikerd.stratego.repl.shell.commands;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.SimpleParser;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class HelpCommand implements CommandMarker{

    @Inject
    private SimpleParser parser;

    @CliCommand(value = {":h", ":help"}, help = "List all commands usage")
    public void help(@CliOption(key = { "", "command" }, optionContext = "disable-string-converter availableCommands", help = "Command name to provide help for")
                                 String buffer){
        parser.obtainHelp(buffer);
    }
}
