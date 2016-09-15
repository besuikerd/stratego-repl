package com.besuikerd.stratego.repl;

import com.besuikerd.stratego.repl.compiler.ICompilationPath;
import com.besuikerd.stratego.repl.compiler.IStrategoCompiler;
import com.besuikerd.stratego.repl.compiler.StrategoToJBCCompiler;
import com.besuikerd.stratego.repl.compiler.TemporyFilePath;
import com.besuikerd.stratego.repl.executer.IStrategoExecuter;
import com.besuikerd.stratego.repl.executer.StrategoExecuter;
import com.besuikerd.stratego.repl.history.ITermHistory;
import com.besuikerd.stratego.repl.history.TermHistory;
import com.besuikerd.stratego.repl.template.IStrategoTemplate;
import com.besuikerd.stratego.repl.template.MustacheStrategoTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.strategoxt.lang.Context;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;

public class ReplModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(IStrategoRepl.class).to(StrategoRepl.class);
        bind(IStrategoCompiler.class).to(StrategoToJBCCompiler.class);
        bind(IStrategoExecuter.class).to(StrategoExecuter.class);
        bind(ITermHistory.class).to(TermHistory.class);
        bind(ICompilationPath.class).to(TemporyFilePath.class);
        bind(MustacheFactory.class).to(DefaultMustacheFactory.class);
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return org.strategoxt.strj.Main.init();
    }

    @Provides
    @Singleton
    public JavaCompiler provideJavaCompiler(){
        return ToolProvider.getSystemJavaCompiler();
    }

    @Provides
    @Singleton
    public IStrategoTemplate provideIStrategoTemplate(MustacheFactory factory, Config config){
        return new MustacheStrategoTemplate(factory, config, "templates/stratego.mustache");
    }

    @Provides
    @Singleton
    public Config provideConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Config config = mapper.readValue(getClass().getClassLoader().getResourceAsStream("conf/application.yaml"), Config.class);
        return config;
    }
}
