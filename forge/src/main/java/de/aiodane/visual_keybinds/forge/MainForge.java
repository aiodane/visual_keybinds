package de.aiodane.visual_keybinds.forge;

import de.aiodane.visual_keybinds.Main;
import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MOD_ID)
public final class MainForge {
    public MainForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Main.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Main.init();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MainForge::registerConfigScreen);

    }
    private static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(
                ExtensionPoint.CONFIGGUIFACTORY,
                () -> (mc, parentScreen) -> Main.createConfigScreen(parentScreen)
        );
    }
}
