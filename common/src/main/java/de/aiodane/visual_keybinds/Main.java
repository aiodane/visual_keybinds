package de.aiodane.visual_keybinds;

import de.aiodane.visual_keybinds.config.ModConfig;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    public static final String MOD_ID = "visual_keybinds";
    public static final Logger LOGGER = LogManager.getLogger("visual_keybinds");
    public static final int CONFIG_VERSION = 1;

    private static ConfigHolder<ModConfig> configHolder;
    public static ModConfig mainConfig;

    public static void init() {
        configHolder = AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        loadMainConfig();
        LOGGER.info("Visual Keybinds initialized successfully!");
    }

    private static void loadMainConfig() {
        mainConfig = configHolder.getConfig();

        if (mainConfig.configVer != CONFIG_VERSION) {
            LOGGER.warn("MainConfig outdated or malformed (expected V" + CONFIG_VERSION + ", got V" + mainConfig.configVer + "), restoring from default...");
            configHolder.resetToDefault();
            configHolder.save();
            mainConfig = configHolder.getConfig();
        }
    }

    public static void saveMainConfig() {
        if (configHolder != null) {
            configHolder.save();
        }
    }

    public static Screen createConfigScreen(Screen parent) {
        return AutoConfig.getConfigScreen(ModConfig.class, parent).get();
    }

    public static TranslatableText t(String key, Object... args) {
        return new TranslatableText(MOD_ID + "." + key, args);
    }
}
