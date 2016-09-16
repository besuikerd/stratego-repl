package com.besuikerd.stratego.repl.shell;

import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider;
import org.springframework.stereotype.Component;

@Component
public class HistoryFilenameProvider extends DefaultHistoryFileNameProvider{
    @Override
    public String getHistoryFileName() {
        return "stratego-repl-history.log";
    }
}
