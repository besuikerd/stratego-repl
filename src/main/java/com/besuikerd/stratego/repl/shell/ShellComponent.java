package com.besuikerd.stratego.repl.shell;

import org.springframework.shell.core.JLineShellComponent;
import org.springframework.shell.core.Parser;
import org.springframework.shell.core.SimpleParser;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;

@Named("shell")
public class ShellComponent extends JLineShellComponent{

    @Inject
    private SimpleParser parser;

    @Override
    protected Parser getParser() {
        return parser;
    }

    @Override
    public SimpleParser getSimpleParser() {
        return parser;
    }
}
