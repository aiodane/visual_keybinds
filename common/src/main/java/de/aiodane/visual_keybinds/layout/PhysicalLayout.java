package de.aiodane.visual_keybinds.layout;

import com.google.gson.Gson;
import de.aiodane.visual_keybinds.Main;
import de.aiodane.visual_keybinds.config.KeyboardSize;
import de.aiodane.visual_keybinds.config.KeyboardStandard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PhysicalLayout {
    private static final Gson GSON = new Gson();

    public float totalWidthU = 20.0f;
    public List<PhysicalKey> keys = new ArrayList<>();

    public static class PhysicalKey {
        public String id;
        public float uX, uY, uWidth = 1.0f, uHeight = 1.0f;
        public boolean isIsoEnter = false;
    }

    public static PhysicalLayout load(KeyboardStandard standard, KeyboardSize size) {
        Identifier layoutId = new Identifier(Main.MOD_ID, "layouts/physical/"+ standard.fileId + "/" + standard.fileId + "_" + size.fileId + ".json");
        try {
            Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(layoutId);
            try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                return GSON.fromJson(reader, PhysicalLayout.class);
            }
        } catch (Exception e) {
            Main.LOGGER.error("Failed to load physical layout from assets: {}", layoutId, e);
            return new PhysicalLayout(); // Fallback empty layout
        }
    }
}