package de.aiodane.visual_keybinds;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main {
    public static final String MOD_ID = "visual_keybinds";

    private static final int langVersion = 1;
    private static final int mainConfigVersion = 1;

    public static FileConfiguration mainConfig;

    public static Logger logger;

    private static File langFile;

    public static FileConfiguration lang;

    public static void init() {
        // Write common init code here.
        logger = getLogger();
        loadMainConfig();
        setupLang();
    }

    private static void loadMainConfig() {
        saveDefaultConfig();
        mainConfig = getConfig();
        if (mainConfig.getInt("config-ver") != mainConfigVersion) {
            logger.log(Level.WARNING, "MainConfig outdated or wrong configured, reloading config from resources...");
            saveResource("config.yml", true);
        }
        mainConfig = getConfig();
    }

    private static void setupLang(){
        String l = mainConfig.getString("language", "en");
        File f = getDataFolder();
        File langFolder = new File(f, "lang");
        if (!langFolder.exists()) {
            try {
                if (!langFolder.mkdirs()) throw new IOException("Could not create directory");
                saveResource("lang/de.yml", false);
                saveResource("lang/en.yml", false);
                logger.log(Level.INFO, "Language Packs could not be found, reloading Packs from resources...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!langFolder.isDirectory()) {
            try {
                FileUtils.forceDelete(langFolder);
                if (!langFolder.mkdirs()) throw new IOException("Could not create directory");
                saveResource("lang/de.yml", false);
                saveResource("lang/en.yml", false);
                logger.log(Level.INFO, "Language Packs could not be found, reloading Packs from resources...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        langFile = new File(getDataFolder()+"/lang/"+ l + ".yml");
        lang = YamlConfiguration.loadConfiguration(langFile);
        if (lang.getInt("lang-ver") != langVersion) {
            logger.log(Level.WARNING, "Language Packs outdated or wrong configured, reloading Packs from resources...");
            saveResource("lang/de.yml", true);
            saveResource("lang/en.yml", true);
        }
        langFile = new File(getDataFolder()+"/lang/"+ l + ".yml");
        lang = YamlConfiguration.loadConfiguration(langFile);
    }

    public static Main getInstance() {
        return instance;
    }
}
