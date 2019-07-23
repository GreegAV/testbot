import java.util.Properties;

public class Config {

    public static String BOT_NAME;
    public static String BOT_TOKEN;

    public static void loadProperties() {
        ConfigManager config = ConfigManager.getInstance();
        System.out.println("Config loaded");

        BOT_NAME = config.getProperty("botName");
        BOT_TOKEN = config.getProperty("botToken");
    }
}
