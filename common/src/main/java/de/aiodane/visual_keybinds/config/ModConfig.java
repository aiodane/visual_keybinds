package de.aiodane.visual_keybinds.config;

import de.aiodane.visual_keybinds.Main;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Main.MOD_ID)
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public static int configVer = Main.CONFIG_VERSION;

    @ConfigEntry.Gui.Tooltip
    public boolean showConflictWarnings = true;

    @ConfigEntry.ColorPicker
    @ConfigEntry.Gui.Tooltip
    public int conflictColor = 0xFF5555;
}
