package com.besuikerd.stratego.repl.shell.commands;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class ReadFileCommand implements CommandMarker {

    @Inject
    private EvalCommand evalCommand;

    @CliCommand(value = {":read"}, help = "load an aterm file and construct it")
    public String readfile(@CliOption(key = "", mandatory = true, help = "file to read") File file){
        if(!file.exists()){
            return "File not found";
        } else{
            try {
                String term = new String(Files.readAllBytes(file.toPath()));
                return evalCommand.eval("!" + term);
            } catch (IOException e) {
                return "Could not read file: " + e.getMessage();
            }
        }
    }
}
