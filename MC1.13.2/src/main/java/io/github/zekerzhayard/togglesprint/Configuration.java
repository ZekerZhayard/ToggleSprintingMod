package io.github.zekerzhayard.togglesprint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.minecraft.launchwrapper.Launch;

public class Configuration {
    public static boolean enableToggleSprint;
    public static boolean enableToggleSneak;
    private static Properties prop = new Properties();
    private static String fileName = Launch.minecraftHome.getAbsolutePath() + File.separator + "ToggleSprinting.properties";

    public static void loadConfig(String fileName) {
        Configuration.fileName = fileName;
        try {
            new File(fileName).createNewFile();
            Configuration.prop.load(new FileInputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration.enableToggleSprint = Boolean.parseBoolean(Configuration.prop.getProperty("toggle.sprint"));
        Configuration.enableToggleSneak = Boolean.parseBoolean(Configuration.prop.getProperty("toggle.sneak"));
        Configuration.saveConfig();
    }

    public static void saveConfig() {
        Configuration.prop.setProperty("toggle.sprint", String.valueOf(Configuration.enableToggleSprint));
        Configuration.prop.setProperty("toggle.sneak", String.valueOf(Configuration.enableToggleSneak));
        try (FileOutputStream fos = new FileOutputStream(Configuration.fileName)) {
            Configuration.prop.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}