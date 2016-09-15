package com.besuikerd.stratego.repl.template;

import com.besuikerd.stratego.repl.Config;
import com.besuikerd.stratego.repl.rule.IStrategoRule;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import javax.inject.Inject;
import java.io.*;

public class MustacheStrategoTemplate implements IStrategoTemplate{

    private Mustache mustache;
    private Config config;

    @Inject
    public MustacheStrategoTemplate(MustacheFactory factory, Config config, String template) {
        this.mustache = factory.compile(template);
        this.config = config;
    }

    @Override
    public InputStream writeTemplate(IStrategoRule rule) {
        StrategoTemplateContext context = new StrategoTemplateContext(config.getImports(), rule.getStringRepresentation(), rule.getIdentity());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mustache.execute(new OutputStreamWriter(bos), context).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(bos.toByteArray());
    }
}
