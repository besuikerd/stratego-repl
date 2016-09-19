package com.besuikerd.stratego.repl.compiler;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Singleton
@Named
public class TemporyFilePath extends AbstractCompilationPath {

    private final Path path;

    public TemporyFilePath() throws IOException {
        this.path = Files.createTempDirectory("stratego-repl");
    }

    @Override
    public Path getPath() {
        return path;
    }

}
