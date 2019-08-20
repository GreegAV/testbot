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

    static final int WECOME_SCREEN = 0;

    static final int EXPENSES_SCREEN = 1;
    static final int EXPENSES_PERSONAL_SCREEN = 11;
    static final int EXPENSES_PERSONAL_SALARY_SCREEN = 111;
    static final int EXPENSES_PERSONAL_STUDY_SCREEN = 112;

    static final int EXPENSES_HOUSEHOLD_SCREEN = 12;
    static final int EXPENSES_HOUSEHOLD_CLEANING_SCREEN = 121;
    static final int EXPENSES_HOUSEHOLD_REPAIR_SCREEN = 122;
    static final int EXPENSES_HOUSEHOLD_GOODS_SCREEN = 123;

    static final int EXPENSES_COMMUNAL_SCREEN = 13;
    static final int EXPENSES_COMMUNAL_RENT_SCREEN = 131;
    static final int EXPENSES_COMMUNAL_ELECTRICITY_SCREEN = 132;
    static final int EXPENSES_COMMUNAL_WATER_SCREEN = 132;
    static final int EXPENSES_COMMUNAL_SERVICE_SCREEN = 134;

    static final int EXPENSES_OFFICE_SCREEN = 14;
    static final int EXPENSES_OFFICE_STATIONARY_SCREEN = 141;
    static final int EXPENSES_OFFICE_CONNECTION_SCREEN = 142;

    static final int EXPENSES_MARKETING_SCREEN = 16;
    static final int EXPENSES_MARKETING_INSTAGRAMM_SCREEN = 161;
    static final int EXPENSES_MARKETING_GOOGLE_SCREEN = 162;
    static final int EXPENSES_MARKETING_FACEBOOK_SCREEN = 163;
    static final int EXPENSES_MARKETING_CRM_SCREEN = 164;
    static final int EXPENSES_MARKETING_ENVATO_SCREEN = 165;
    static final int EXPENSES_MARKETING_TILDA_SCREEN = 166;
    static final int EXPENSES_MARKETING_OTHER_SCREEN = 167;

    static final int EXPENSES_TAXES_SCREEN = 17;
    static final int EXPENSES_TAXES_ESV_SCREEN = 171;
    static final int EXPENSES_TAXES_NDFL_SCREEN = 172;
    static final int EXPENSES_TAXES_WAR_SCREEN = 173;
    static final int EXPENSES_TAXES_EDIN_SCREEN = 174;
    static final int EXPENSES_TAXES_VAT_SCREEN = 175;

    static final int EXPENSES_DIFFERENT = 10;

    static final int INCOME_SCREEN = 2;
    static final int REVENUE_SCREEN = 20;
    static final int OTHERREVENUE_SCREEN = 21;


    static void loadProperties() {
        Config config = Config.getInstance();

        BOT_NAME = config.getProperty("botName");
        BOT_TOKEN = config.getProperty("botToken");
        APPLICATION_NAME = config.getProperty("appName");
        SPREADSHEET_URL = config.getProperty("sheetsURL");

        System.out.println("Config for " + BOT_NAME + " loaded");
    }
}
