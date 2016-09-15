package com.besuikerd.stratego.repl.compiler;

import java.nio.file.Path;

public interface ICompilationPath {
    public Path getPath();
    public String getClassPath();
}