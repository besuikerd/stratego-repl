package com.besuikerd.stratego.repl.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.FileUtils;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BannerProvider extends DefaultBannerProvider{

    @Override
    public String getBanner() {
        return FileUtils.readBanner(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("banner.txt")));
    }

    @Override
    public String getWelcomeMessage() {
        return "enter :h for a list of available commands";
    }
}
