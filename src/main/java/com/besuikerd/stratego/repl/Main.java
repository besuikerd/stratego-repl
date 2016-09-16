package com.besuikerd.stratego.repl;

import com.besuikerd.stratego.repl.shell.ShellComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.tools.ToolProvider;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        if(checkCapabilities()){
            new Main().run(args);
        }
    }

    public static boolean checkCapabilities(){
        if(ToolProvider.getSystemJavaCompiler() == null){
            System.out.println("Could not find javac. Make sure you have a java compiler");
            return false;
        }
        return true;
    }

    private void run(String[] args) throws IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ShellComponent shell = ctx.getBean(ShellComponent.class);
        shell.start();
        shell.promptLoop();
        shell.waitForComplete();
    }
}
