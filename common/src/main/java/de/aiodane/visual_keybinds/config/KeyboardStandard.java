package de.aiodane.visual_keybinds.config;

public enum KeyboardStandard {
    ISO("iso"),
    ANSI("ansi"),
    JIS("jis");

    public final String fileId;

    KeyboardStandard(String fileId) {
        this.fileId = fileId;
    }
}
