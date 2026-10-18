package de.aiodane.visual_keybinds.config;

public enum KeyboardSize {
    SIZE_100("100"),
    SIZE_80_TKL("tkl"),
    SIZE_75("75"),
    SIZE_60("60");

    public final String fileId;

    KeyboardSize(String fileId) {
        this.fileId = fileId;
    }
}
