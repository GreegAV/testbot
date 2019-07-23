import java.util.ResourceBundle;

public class ConfigManager {


    private static final String BUNDLE_NAME = "config";
    private static ConfigManager instance;
    private ResourceBundle resource;

    private ConfigManager() {

    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
