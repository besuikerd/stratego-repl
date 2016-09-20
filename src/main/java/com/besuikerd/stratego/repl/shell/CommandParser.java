package com.besuikerd.stratego.repl.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.core.SimpleParser;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.logging.Logger;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommandParser extends SimpleParser{

    private ICommandNotFound fallback;

    @Inject
    public CommandParser(ICommandNotFound fallback) {
        this.fallback = fallback;
    }

    @Override
    protected void commandNotFound(Logger logger, String command) {
        fallback.fallback(command);
    }
}
