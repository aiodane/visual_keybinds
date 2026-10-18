package de.aiodane.visual_keybinds.layout;

import de.aiodane.visual_keybinds.config.KeyboardSize;
import de.aiodane.visual_keybinds.config.KeyboardStandard;
import de.aiodane.visual_keybinds.util.RenderKey;

import java.util.ArrayList;
import java.util.List;

public class KeyboardLayout {
    public final float totalWidthU;
    public final List<RenderKey> keys = new ArrayList<>();

    private KeyboardLayout(float totalWidthU) {
        this.totalWidthU = totalWidthU;
    }

    /**
     * Merges physical and virtual layouts into a single renderable keyboard.
     */
    public static KeyboardLayout build(KeyboardStandard standard, KeyboardSize size, String virtualLanguageId) {
        PhysicalLayout physical = PhysicalLayout.load(standard, size);
        VirtualLayout virtual = VirtualLayout.load(virtualLanguageId);

        KeyboardLayout layout = new KeyboardLayout(physical.totalWidthU);

        for (PhysicalLayout.PhysicalKey pKey : physical.keys) {
            // Get the virtual key mapping, or use a fallback if the ID is missing in the config
            VirtualLayout.VirtualKey vKey = virtual.mappings.getOrDefault(pKey.id, new VirtualLayout.VirtualKey(0, ""));

            layout.keys.add(new RenderKey(
                    pKey.id,
                    pKey.uX, pKey.uY, pKey.uWidth, pKey.uHeight, pKey.isIsoEnter,
                    vKey.keyCode,
                    vKey.label
            ));
        }

        return layout;
    }
}