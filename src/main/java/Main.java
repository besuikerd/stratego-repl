import com.besuikerd.stratego.repl.Config;
import com.besuikerd.stratego.repl.IStrategoRepl;
import com.besuikerd.stratego.repl.ReplModule;
import com.besuikerd.stratego.repl.StrategoRepl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.strategoxt.lang.Context;

import javax.tools.ToolProvider;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

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
        Injector injector = Guice.createInjector(new ReplModule());
        IStrategoRepl repl = injector.getInstance(IStrategoRepl.class);
        while(true){
            repl.repl();
        }
    }
}
