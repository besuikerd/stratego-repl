package com.besuikerd.stratego.repl.shell.commands;

import com.besuikerd.stratego.repl.compiler.ICompilationPath;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class CompilationPathCommand implements CommandMarker{

    private ICompilationPath compilationPath;

    @Inject
    public CompilationPathCommand(ICompilationPath compilationPath) {
        this.compilationPath = compilationPath;
    }

    @CliCommand(value = ":compilepath", help = "prints out path where intermediate files are compiled to")
    public String compilationPath(){
        return compilationPath.getPath().toUri().toString();
    }
}
