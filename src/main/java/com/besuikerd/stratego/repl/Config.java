package com.besuikerd.stratego.repl;

import java.util.List;

public class Config {
    private List<String> imports;

    public Config() {
    }

    public Config(List<String> imports) {
        this.imports = imports;
    }

    public List<String> getImports() {
        return imports;
    }

    @Override
    public String toString() {
        return "Config{" +
                "imports=" + imports +
                '}';
    }
}
