package de.aiodane.visual_keybinds.screen;

import de.aiodane.visual_keybinds.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class KeyboardViewScreen extends Screen {
    private final Screen parent;

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public KeyboardViewScreen(Screen parent) {
        super(Main.t("screen.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        // "Zurück"-Button unten in der Mitte
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 30, 200, 20,
                new TranslatableText("gui.back"), btn -> client.openScreen(this.parent)));
    }

    // Sucht alle Aktionen, die auf diesen GLFW-Keycode gelegt sind
    private List<KeyBinding> getBindingsForKey(int keyCode) {
        List<KeyBinding> matches = new ArrayList<>();
        for (KeyBinding binding : client.options.keysAll) {
            // Prüft, ob der Keycode mit der zugewiesenen Taste übereinstimmt
            if (binding.matchesKey(keyCode, 0)) {
                matches.add(binding);
            }
        }
        return matches;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        // Titel zentriert oben zeichnen
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);


        int startX = this.width / 2;
        int startY = 60;

        Text tooltipToRender = null;


        super.render(matrices, mouseX, mouseY, delta);
    }
}