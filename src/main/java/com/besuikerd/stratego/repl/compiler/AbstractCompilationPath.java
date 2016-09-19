package com.besuikerd.stratego.repl.compiler;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class AbstractCompilationPath implements ICompilationPath{
    @Override
    public String getClassPath(){
        //include stratego compiler in the classpath as a dependency
        URL strategoCompilerURL = getClass().getClassLoader().getResource("org/strategoxt/lang/Context.class");
        String strategoCompilerPath = strategoCompilerURL.getPath().replaceFirst("^file:(.*)!.*$", "$1");
        return strategoCompilerPath + File.pathSeparatorChar + getPath().toFile().getAbsolutePath();
    }
}
