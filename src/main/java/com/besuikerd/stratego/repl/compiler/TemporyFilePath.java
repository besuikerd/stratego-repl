package com.besuikerd.stratego.repl.compiler;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Singleton
@Named
public class TemporyFilePath implements ICompilationPath {

    private final Path path;

    public TemporyFilePath() throws IOException {
        this.path = Files.createTempDirectory("stratego-repl");
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public String getClassPath(){
        //include yourself as a classpath dependency when compiling
        String codeSource = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String strategoCompiler =
            codeSource.endsWith(".jar") ? codeSource //ran from jar file
            : getClass().getClassLoader().getResource("strategoxt.jar").getPath(); //ran during development
        return strategoCompiler + File.pathSeparatorChar + path.toFile().getAbsolutePath();
    }
}
