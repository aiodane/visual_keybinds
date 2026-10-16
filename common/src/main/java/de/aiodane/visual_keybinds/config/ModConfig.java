package de.aiodane.visual_keybinds.config;

import de.aiodane.visual_keybinds.Main;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Main.MOD_ID)
public class ModConfig implements ConfigData {

    public static int configVer = Main.CONFIG_VERSION;

    @ConfigEntry.Gui.Tooltip
    @Comment("Sollen Tasten mit Mehrfachbelegung farbig hervorgehoben werden?")
    public boolean showConflictWarnings = true;

    @ConfigEntry.ColorPicker
    @Comment("Farbe für doppelt belegte Tasten (Hex-Format)")
    public int conflictColor = 0xFF5555;
}
