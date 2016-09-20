package com.besuikerd.stratego.repl.shell;

import com.besuikerd.stratego.repl.history.ITermHistory;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class PromptProvider extends DefaultPromptProvider{

    ITermHistory history;

    @Inject
    public PromptProvider(ITermHistory history) {
        this.history = history;
    }

    @Override
    public String getPrompt() {
        return "stratego>";
    }
}
