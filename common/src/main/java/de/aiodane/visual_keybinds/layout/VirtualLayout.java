package de.aiodane.visual_keybinds.layout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.aiodane.visual_keybinds.Main;
import me.shedaniel.architectury.platform.Platform;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class VirtualLayout {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Maps the PhysicalKey ID (e.g., "K1") to its virtual assignment
    public Map<String, VirtualKey> mappings = new HashMap<>();

    public static class VirtualKey {
        public int keyCode;
        public String label;

        public VirtualKey() {}

        public VirtualKey(int keyCode, String label) {
            this.keyCode = keyCode;
            this.label = label;
        }
    }

    public static VirtualLayout load(String languageId) {
        File dir = Platform.getConfigFolder().resolve(Main.MOD_ID).resolve("layouts").resolve("virtual").toFile();
        if (!dir.exists()) dir.mkdirs();

        File file = new File(dir, languageId + ".json");

        if (!file.exists()) {
            Main.LOGGER.warn("Virtual layout {} not found. Generating dummy template...", file.getAbsolutePath());
            VirtualLayout template = new VirtualLayout();
            template.mappings.put("K_EXAMPLE", new VirtualKey(0, "?"));

            try (FileWriter writer = new FileWriter(file)) {
                GSON.toJson(template, writer);
            } catch (Exception e) {
                Main.LOGGER.error("Failed to generate virtual layout template!", e);
            }
            return template;
        }

        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, VirtualLayout.class);
        } catch (Exception e) {
            Main.LOGGER.error("Failed to load virtual layout: {}", file.getAbsolutePath(), e);
            return new VirtualLayout();
        }
    }
}