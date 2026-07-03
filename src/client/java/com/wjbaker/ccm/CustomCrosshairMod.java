package com.wjbaker.ccm;

import com.mojang.blaze3d.platform.InputConstants;
import com.wjbaker.ccm.config.ConfigManager;
import com.wjbaker.ccm.config.GlobalProperties;
import com.wjbaker.ccm.gui.screen.screens.editCrosshair.EditCrosshairGuiScreen;
import com.wjbaker.ccm.helpers.Helper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.concurrent.Executors;

import static org.apache.logging.log4j.LogManager.getLogger;

public final class CustomCrosshairMod implements ModInitializer {

    public static CustomCrosshairMod INSTANCE;

    public static final String TITLE = "Custom Crosshair Mod";
    public static final String VERSION = "1.6.7-fabric";
    public static final String MC_VERSION = "26.2-fabric";
    public static final String CURSEFORGE_PAGE = "https://www.curseforge.com/projects/242995/";
    public static final String MC_FORUMS_PAGE = "https://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2637819/";
    public static final String PATREON_PAGE = "https://www.patreon.com/bePatron?u=66431720";
    public static final String PAYPAL_PAGE = "https://www.paypal.com/donate/?hosted_button_id=KSDHLTS64X5G2";

    private final Logger logger;
    private final Minecraft mc;
    private final GlobalProperties properties;

    private ConfigManager configManager;

    public CustomCrosshairMod() {
        this.logger = getLogger(CustomCrosshairMod.class);
        this.mc = Minecraft.getInstance();
        this.properties = new GlobalProperties();
    }

    @Override
    public void onInitialize() {
        INSTANCE = this;

        this.loadConfig();
        this.checkVersionAsync();
        this.loadKeyBindings();

        this.properties.getCustomCrosshairDrawer().loadImage();
    }

    private void loadConfig() {
        this.configManager = new ConfigManager(
            "crosshair_config.ccmcfg",
            this.properties.getCrosshair(),
            this.properties.getIsModEnabled());

        if (!this.configManager.read()) {
            if (!this.configManager.write()) {
                this.error("Config Manager (Load)", "Unable to load or write config.");
            }
        }
    }

    private void checkVersionAsync() {
        Executors.newSingleThreadExecutor().submit(this::checkVersion);
    }

    private void checkVersion() {
        try (final var reader = Helper.getUrl("https://pastebin.com/raw/B2sL8QCh")) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                var lineSplit = currentLine.split(" ");

                if (lineSplit.length != 2)
                    continue;

                var mcVersion = lineSplit[0];
                var expectedModVersion = lineSplit[1];

                if (mcVersion.equals(MC_VERSION) && !expectedModVersion.equals(VERSION)) {
                    this.log("Version Checker", "Not using latest version of Customer Crosshair Mod.");
                    this.properties.setLatestVersion(false);
                }
            }
        }
        catch (final IOException e) {
            this.error("Version Checker", "Unable to check the version.");
        }
    }

    private void loadKeyBindings() {
        var editCrosshair = new KeyMapping(
            "keybind.custom_crosshair_mod.open_edit_crosshair_gui",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_GRAVE_ACCENT,
            new KeyMapping.Category(Identifier.fromNamespaceAndPath("custom_crosshair_mod", "key_bindings")));

        KeyMappingHelper.registerKeyMapping(editCrosshair);

        ClientTickEvents.END_CLIENT_TICK.register(e -> {
            if (this.mc.gui.screen() == null && editCrosshair.isDown())
                this.mc.setScreenAndShow(new EditCrosshairGuiScreen(this.properties.getCrosshair()));
        });
    }

    public GlobalProperties properties() {
        return this.properties;
    }

    public ConfigManager configManager() {
        return this.configManager;
    }

    public void log(final String action, final String message, final Object... values) {
        this.logger.info(String.format("[%s] %s", action, message), values);
    }

    public void error(final String action, final String message, final Object... values) {
        this.logger.error(String.format("[%s] %s", action, message), values);
    }
}