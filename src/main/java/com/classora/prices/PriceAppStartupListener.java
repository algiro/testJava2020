package com.classora.prices;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.classora.prices.app_config.DbConfig;

@Component
public class PriceAppStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private final DbConfig dbConfig;

    public PriceAppStartupListener(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        dbConfig.init();
    }
}