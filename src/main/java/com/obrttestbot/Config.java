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
    static int screenNumber = 0;

    static final int WELCOME_SCREEN = 0;

    static final int EXPENSES_SCREEN = 1;
    static final int EXPENSES_PERSONAL_SCREEN = 11;
    static final int EXPENSES_PERSONAL_SALARY = 111;
    static final int EXPENSES_PERSONAL_STUDY = 112;

    static final int EXPENSES_HOUSEHOLD_SCREEN = 12;
    static final int EXPENSES_HOUSEHOLD_CLEANING = 121;
    static final int EXPENSES_HOUSEHOLD_REPAIR = 122;
    static final int EXPENSES_HOUSEHOLD_GOODS = 123;

    static final int EXPENSES_COMMUNAL_SCREEN = 13;
    static final int EXPENSES_COMMUNAL_RENT = 131;
    static final int EXPENSES_COMMUNAL_ELECTRICITY = 132;
    static final int EXPENSES_COMMUNAL_WATER = 133;
    static final int EXPENSES_COMMUNAL_SERVICE = 134;

    static final int EXPENSES_OFFICE_SCREEN = 14;
    static final int EXPENSES_OFFICE_STATIONARY = 141;
    static final int EXPENSES_OFFICE_CONNECTION = 142;

    static final int EXPENSES_MARKETING_SCREEN = 16;
    static final int EXPENSES_MARKETING_INSTAGRAMM = 161;
    static final int EXPENSES_MARKETING_GOOGLE = 162;
    static final int EXPENSES_MARKETING_FACEBOOK = 163;
    static final int EXPENSES_MARKETING_CRM = 164;
    static final int EXPENSES_MARKETING_ENVATO = 165;
    static final int EXPENSES_MARKETING_TILDA = 166;
    static final int EXPENSES_MARKETING_OTHER = 167;

    static final int EXPENSES_TAXES_SCREEN = 17;
    static final int EXPENSES_TAXES_ESV = 171;
    static final int EXPENSES_TAXES_NDFL = 172;
    static final int EXPENSES_TAXES_WAR = 173;
    static final int EXPENSES_TAXES_EDIN = 174;
    static final int EXPENSES_TAXES_VAT = 175;

    static final int EXPENSES_DIFFERENT = 10;

    static final int INCOME_SCREEN = 2;
    static final int INCOME_REVENUE = 200;
    static final int INCOME_OTHERREVENUE = 210;


    static void loadProperties() {
        Config config = Config.getInstance();

        BOT_NAME = config.getProperty("botName");
        BOT_TOKEN = config.getProperty("botToken");
        APPLICATION_NAME = config.getProperty("appName");
        SPREADSHEET_URL = config.getProperty("sheetsURL");

        System.out.println("Config for " + BOT_NAME + " loaded");
    }
}
