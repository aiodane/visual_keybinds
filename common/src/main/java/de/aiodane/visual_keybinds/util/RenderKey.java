package de.aiodane.visual_keybinds.util;

public class RenderKey {
    public final String id;
    public final float uX, uY, uWidth, uHeight;
    public final boolean isIsoEnter;

    public final int keyCode;
    public final String label;

    public RenderKey(String id, float uX, float uY, float uWidth, float uHeight, boolean isIsoEnter, int keyCode, String label) {
        this.id = id;
        this.uX = uX;
        this.uY = uY;
        this.uWidth = uWidth;
        this.uHeight = uHeight;
        this.isIsoEnter = isIsoEnter;
        this.keyCode = keyCode;
        this.label = label;
    }
}