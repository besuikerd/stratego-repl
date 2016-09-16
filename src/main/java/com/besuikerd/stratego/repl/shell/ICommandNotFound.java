package com.besuikerd.stratego.repl.shell;

public interface ICommandNotFound {
    public void fallback(String command);
}
