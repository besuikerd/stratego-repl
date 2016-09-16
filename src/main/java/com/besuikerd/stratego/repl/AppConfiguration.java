package com.besuikerd.stratego.repl;


import com.besuikerd.stratego.repl.template.IStrategoTemplate;
import com.besuikerd.stratego.repl.template.MustacheStrategoTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.mustachejava.DefaultMustacheFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.CommandLine;
import org.springframework.shell.SimpleShellCommandLineOptions;
import org.strategoxt.lang.Context;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;

@Configuration
public class AppConfiguration {
    @Bean
    public CommandLine provideCommandLine() throws IOException {
        return SimpleShellCommandLineOptions.parseCommandLine(new String[0]);
    }

    @Bean
    public Config provideConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Config config = mapper.readValue(getClass().getClassLoader().getResourceAsStream("conf/application.yaml"), Config.class);
        return config;
    }

    @Bean
    public Object configure(AnnotationConfigApplicationContext ctx){
        ctx.scan(
                "org.springframework.shell.converters",
                getClass().getPackage().getName()
        );
        return null;
    }

    @Bean
    public JavaCompiler javaCompiler(){
        return ToolProvider.getSystemJavaCompiler();
    }

    @Bean
    public IStrategoTemplate provideTemplate(Config config){
        return new MustacheStrategoTemplate(new DefaultMustacheFactory(), config, "templates/stratego.mustache");
    }

    @Bean
    public Context provideContext(){
        return org.strategoxt.strj.Main.init();
    }
}