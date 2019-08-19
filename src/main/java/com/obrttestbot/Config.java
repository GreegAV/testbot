package com.obrttestbot;

import java.util.ResourceBundle;

class Config {

    private static final String BUNDLE_NAME = "config";
    private static Config instance;
    private ResourceBundle resource;

    private Config() {
    }

    private static Config getInstance() {
        if (instance == null) {
            instance = new Config();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    private String getProperty(String key) {
        return (String) resource.getObject(key);
    }

    static String BOT_NAME;
    static String BOT_TOKEN;
    static String APPLICATION_NAME;
    static String SPREADSHEET_URL;
    static int screenNumber=0;

    static void loadProperties() {
        Config config = Config.getInstance();

        BOT_NAME = config.getProperty("botName");
        BOT_TOKEN = config.getProperty("botToken");
        APPLICATION_NAME = config.getProperty("appName");
        SPREADSHEET_URL = config.getProperty("sheetsURL");

        System.out.println("Config for " + BOT_NAME + " loaded");
    }
}
