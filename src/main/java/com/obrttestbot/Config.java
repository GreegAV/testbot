package com.obrttestbot;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

class Config {

    private static final String BUNDLE_NAME = "config";
    static Map<String, Integer> buttonsNumbers = new HashMap<>();
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
    static int lastScreen = 0;

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
    static final int EXPENSES_TAXES_VAT = 172;
    static final int EXPENSES_TAXES_NDFL = 173;
    static final int EXPENSES_TAXES_EDIN = 174;
    static final int EXPENSES_TAXES_WAR = 175;

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

        buttonsNumbers.put("Расходы", Config.EXPENSES_SCREEN);

        buttonsNumbers.put("На персонал", Config.EXPENSES_PERSONAL_SCREEN);
        buttonsNumbers.put("Зарплата", Config.EXPENSES_PERSONAL_SALARY);
        buttonsNumbers.put("Обучение", Config.EXPENSES_PERSONAL_STUDY);

        buttonsNumbers.put("Хозяйственные", Config.EXPENSES_HOUSEHOLD_SCREEN);
        buttonsNumbers.put("Уборка", Config.EXPENSES_HOUSEHOLD_CLEANING);
        buttonsNumbers.put("Ремонты", Config.EXPENSES_HOUSEHOLD_REPAIR);
        buttonsNumbers.put("Хозтовары", Config.EXPENSES_HOUSEHOLD_GOODS);

        buttonsNumbers.put("Коммуналка", Config.EXPENSES_COMMUNAL_SCREEN);
        buttonsNumbers.put("Аренда", Config.EXPENSES_COMMUNAL_RENT);
        buttonsNumbers.put("Электричество", Config.EXPENSES_COMMUNAL_ELECTRICITY);
        buttonsNumbers.put("Вода", Config.EXPENSES_COMMUNAL_WATER);
        buttonsNumbers.put("Обслуживание", Config.EXPENSES_COMMUNAL_SERVICE);

        buttonsNumbers.put("Офис", Config.EXPENSES_OFFICE_SCREEN);
        buttonsNumbers.put("Канцтовары", Config.EXPENSES_OFFICE_STATIONARY);
        buttonsNumbers.put("Связь", Config.EXPENSES_OFFICE_CONNECTION);

        buttonsNumbers.put("Маркетинг", Config.EXPENSES_MARKETING_SCREEN);
        buttonsNumbers.put("Instagram", Config.EXPENSES_MARKETING_INSTAGRAMM);
        buttonsNumbers.put("Google", Config.EXPENSES_MARKETING_GOOGLE);
        buttonsNumbers.put("Facebook", Config.EXPENSES_MARKETING_FACEBOOK);
        buttonsNumbers.put("CRM", Config.EXPENSES_MARKETING_CRM);
        buttonsNumbers.put("Envato", Config.EXPENSES_MARKETING_ENVATO);
        buttonsNumbers.put("Tilda", Config.EXPENSES_MARKETING_TILDA);
        buttonsNumbers.put("Прочее", Config.EXPENSES_MARKETING_OTHER);

        buttonsNumbers.put("Налоги", Config.EXPENSES_TAXES_SCREEN);
        buttonsNumbers.put("ЕСВ", Config.EXPENSES_TAXES_ESV);
        buttonsNumbers.put("НДФЛ", Config.EXPENSES_TAXES_NDFL);
        buttonsNumbers.put("Военный налог", Config.EXPENSES_TAXES_WAR);
        buttonsNumbers.put("Единый налог", Config.EXPENSES_TAXES_EDIN);
        buttonsNumbers.put("НДС", Config.EXPENSES_TAXES_VAT);

        buttonsNumbers.put("Другие расходы", Config.EXPENSES_DIFFERENT);

        buttonsNumbers.put("Доходы", Config.INCOME_SCREEN);
        buttonsNumbers.put("Выручка от продаж", Config.INCOME_REVENUE);
        buttonsNumbers.put("Прочие доходы", Config.INCOME_OTHERREVENUE);


        System.out.println("Config for " + BOT_NAME + " loaded");
    }
}
