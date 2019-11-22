package com.obrttestbot;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Config {

    private static final String BUNDLE_NAME = "config";

    public static Map<String, Integer> buttonsNumbers = new HashMap<>();
    static Map<Integer, String> namesAndIDs = new HashMap<>();
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

    public static String getSheetNameByUserID(int i) {
        return namesAndIDs.get(i) == null ? "General" : namesAndIDs.get(i);
    }

    private String getProperty(String key) {
        return (String) resource.getObject(key);
    }


    static String BOT_NAME;
    static String BOT_TOKEN;
    public static String APPLICATION_NAME;
    public static String SPREADSHEET_URL;
    public static String BILLTEMPLATE_URL;
    public static int BILLTEMPLATESHEETID;
    public static int screenNumber = 0;
    public static int lastScreen = 0;

    //   0      1           2          3       4      5        6          7
    // Дата	Расшифровка	Контрагент	Приход	Расход	Всего	Вид ДДС	Статья ДДС
    public static String[] resultString = new String[8];

    public static boolean enteringSumm = false;
    public static boolean fillingBudget = false;
    public static boolean waitingForContragent = false;
    public static boolean enteringDetailsOfExpences = false;

    public static final int WELCOME_SCREEN = 0;

    public static final int EXPENSES_SCREEN = 1;
    public static final int EXPENSES_PERSONAL_SCREEN = 11;
    public static final int EXPENSES_PERSONAL_SALARY = 111;
    public static final int EXPENSES_PERSONAL_STUDY = 112;

    public static final int EXPENSES_HOUSEHOLD_SCREEN = 12;
    public static final int EXPENSES_HOUSEHOLD_CLEANING = 121;
    public static final int EXPENSES_HOUSEHOLD_REPAIR = 122;
    public static final int EXPENSES_HOUSEHOLD_GOODS = 123;

    public static final int EXPENSES_COMMUNAL_SCREEN = 13;
    public static final int EXPENSES_COMMUNAL_RENT = 131;
    public static final int EXPENSES_COMMUNAL_ELECTRICITY = 132;
    public static final int EXPENSES_COMMUNAL_WATER = 133;
    public static final int EXPENSES_COMMUNAL_SERVICE = 134;

    public static final int EXPENSES_OFFICE_SCREEN = 14;
    public static final int EXPENSES_OFFICE_STATIONARY = 141;
    public static final int EXPENSES_OFFICE_CONNECTION = 142;

    public static final int EXPENSES_MARKETING_SCREEN = 16;
    public static final int EXPENSES_MARKETING_INSTAGRAMM = 161;
    public static final int EXPENSES_MARKETING_GOOGLE = 162;
    public static final int EXPENSES_MARKETING_FACEBOOK = 163;
    public static final int EXPENSES_MARKETING_CRM = 164;
    public static final int EXPENSES_MARKETING_ENVATO = 165;
    public static final int EXPENSES_MARKETING_TILDA = 166;
    public static final int EXPENSES_MARKETING_OTHER = 167;

    public static final int EXPENSES_TAXES_SCREEN = 17;
    public static final int EXPENSES_TAXES_ESV = 171;
    public static final int EXPENSES_TAXES_VAT = 172;
    public static final int EXPENSES_TAXES_NDFL = 173;
    public static final int EXPENSES_TAXES_EDIN = 174;
    public static final int EXPENSES_TAXES_WAR = 175;

    public static final int EXPENSES_DIFFERENT = 10;

    public static final int INCOME_SCREEN = 2;
    public static final int INCOME_REVENUE = 20;
    public static final int INCOME_OTHERREVENUE = 21;

    public static final int TOTAL_SCREEN = 3;

    public static final int EXIT = 999;

    static void loadProperties() {
        namesAndIDs.put(221816696, "OBRT");         // GreegAV
        namesAndIDs.put(269463036, "ДНК ЭДЖЕНСИ");  //  dma_k
        namesAndIDs.put(224606811, "ДНК ЭДЖЕНСИ");  // Анастасия
        namesAndIDs.put(548498472, "ДНК ЭДЖЕНСИ");  // Росочинская
        namesAndIDs.put(515273686, "Алла");         // Алла
        namesAndIDs.put(148370030, "ДНК ЭДЖЕНСИ");  // Батова

        for (int i = 0; i < 8; i++) {
            resultString[i] = " ";
        }

        Config config = Config.getInstance();

        BOT_NAME = config.getProperty("botName");
        BOT_TOKEN = config.getProperty("botToken");
        APPLICATION_NAME = config.getProperty("appName");
        SPREADSHEET_URL = config.getProperty("sheetsURL");
        BILLTEMPLATE_URL = config.getProperty("billTemplate");
        BILLTEMPLATESHEETID = Integer.parseInt(config.getProperty("billTemplateSheetID"));

        buttonsNumbers.put("Прекратить ввод", Config.EXIT);
        buttonsNumbers.put("Расходы", Config.EXPENSES_SCREEN);

        buttonsNumbers.put("Персонал", Config.EXPENSES_PERSONAL_SCREEN);
        buttonsNumbers.put("Зарплата", Config.EXPENSES_PERSONAL_SALARY);
        buttonsNumbers.put("Обучение", Config.EXPENSES_PERSONAL_STUDY);

        buttonsNumbers.put("Хозяйственные", Config.EXPENSES_HOUSEHOLD_SCREEN);
        buttonsNumbers.put("Уборка", Config.EXPENSES_HOUSEHOLD_CLEANING);
        buttonsNumbers.put("Ремонты", Config.EXPENSES_HOUSEHOLD_REPAIR);
        buttonsNumbers.put("Хозтовары", Config.EXPENSES_HOUSEHOLD_GOODS);

        buttonsNumbers.put("Коммунальные", Config.EXPENSES_COMMUNAL_SCREEN);
        buttonsNumbers.put("Аренда", Config.EXPENSES_COMMUNAL_RENT);
        buttonsNumbers.put("Электричество", Config.EXPENSES_COMMUNAL_ELECTRICITY);
        buttonsNumbers.put("Вода", Config.EXPENSES_COMMUNAL_WATER);
        buttonsNumbers.put("Обслуживание", Config.EXPENSES_COMMUNAL_SERVICE);

        buttonsNumbers.put("Офисные", Config.EXPENSES_OFFICE_SCREEN);
        buttonsNumbers.put("Канцтовары", Config.EXPENSES_OFFICE_STATIONARY);
        buttonsNumbers.put("Связь", Config.EXPENSES_OFFICE_CONNECTION);

        buttonsNumbers.put("Маркетинговые", Config.EXPENSES_MARKETING_SCREEN);
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

        buttonsNumbers.put("Итого баланс", Config.TOTAL_SCREEN);

        System.out.println("Config for " + BOT_NAME + " loaded");
    }

}
