package de.aiodane.visual_keybinds.config;

import de.aiodane.visual_keybinds.Main;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Main.MOD_ID)
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public static int configVer = Main.CONFIG_VERSION;

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    @ConfigEntry.Gui.Excluded
    @Comment("Architecture of the keyboard (e. g. ISO for Europe / UK, ANSI for US)")
    public KeyboardStandard activeKeyboardStandard = KeyboardStandard.ANSI;

    @ConfigEntry.Gui.Excluded
    @Comment("Labeling and Language Layout of the keyboard. Must match a directory in %MINECRAFT_INSTANCE%/config/visual_keybinds/layouts/")
    public String activeKeyboardLanguage = "en_us";

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    @ConfigEntry.Gui.Excluded
    @Comment("Size of the keyboard (100% = Full size keyboard, 80%_TKL = No numpad)")
    public KeyboardSize activeKeyboardSize = KeyboardSize.SIZE_100;

    @ConfigEntry.Gui.Tooltip
    public boolean showConflictWarnings = true;

    @ConfigEntry.ColorPicker
    @ConfigEntry.Gui.Tooltip
    public int conflictColor = 0xFF5555;
}
