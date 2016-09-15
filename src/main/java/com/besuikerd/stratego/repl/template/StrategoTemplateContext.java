package com.besuikerd.stratego.repl.template;

import java.util.List;

public class StrategoTemplateContext {
    private List<String> imports;
    private String rule;
    private String moduleName;

    public StrategoTemplateContext(List<String> imports, String rule, String moduleName) {
        this.imports = imports;
        this.rule = rule;
        this.moduleName = moduleName;
    }

    public List<String> imports(){
        return imports;
    }

    public String rule(){
        return rule;
    }

    public String moduleName(){
        return moduleName;
    }
}
