import java.util.ResourceBundle;

public class Config {

    private static final String BUNDLE_NAME = "config";
    private static Config instance;
    private ResourceBundle resource;

    private Config() {
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }

    public static String BOT_NAME;
    public static String BOT_TOKEN;
    public static String APPLICATION_NAME;
    public static String SPREADSHEET_URL;

    public static void loadProperties() {
        Config config = Config.getInstance();

        BOT_NAME = config.getProperty("botName");
        BOT_TOKEN = config.getProperty("botToken");
        APPLICATION_NAME = config.getProperty("appName");
        SPREADSHEET_URL = config.getProperty("sheetsURL");

        System.out.println("Config loaded");
    }
}
