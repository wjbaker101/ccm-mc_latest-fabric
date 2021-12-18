package com.wjbaker.ccm;

import com.wjbaker.ccm.config.ConfigManager;
import com.wjbaker.ccm.config.GlobalProperties;
import com.wjbaker.ccm.crosshair.property.ICrosshairProperty;
import com.wjbaker.ccm.helper.RequestHelper;
import com.wjbaker.ccm.render.gui.screen.screens.editCrosshair.EditCrosshairGuiScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.LogManager.getLogger;

public final class CustomCrosshairMod implements ModInitializer {

    public static CustomCrosshairMod INSTANCE;

    public static final String TITLE = "Custom Crosshair Mod";
    public static final String VERSION = "1.3.9-fabric";
    public static final String MC_VERSION = "1.18.1-fabric";
    public static final String CURSEFORGE_PAGE = "https://www.curseforge.com/projects/242995/";
    public static final String MC_FORUMS_PAGE = "https://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2637819/";

    private final Logger logger;
    private final MinecraftClient mc;
    private final GlobalProperties properties;

    private ConfigManager configManager;

    public CustomCrosshairMod() {
        this.logger = getLogger(CustomCrosshairMod.class);
        this.mc = MinecraftClient.getInstance();
        this.properties = new GlobalProperties();
    }

    @Override
    public void onInitialize() {
        INSTANCE = this;

        this.loadConfig();
        this.checkVersion();
        this.loadKeyBindings();
    }

    private void loadConfig() {
        var configProperties = this.properties.getCrosshair().propertiesAsList;
        configProperties.add(this.properties.getIsModEnabled());
        configProperties.add(this.properties.getBlockOutlineColour());

        this.configManager = new ConfigManager("crosshair_config.ccmcfg", configProperties
            .stream()
            .collect(Collectors.toMap(ICrosshairProperty::alias, p -> p)));

        if (!this.configManager.read()) {
            if (!this.configManager.write()) {
                this.error("Config Manager (Load)", "Unable to load or write config.");
            }
        }
    }

    private void checkVersion() {
        try (final var reader = new RequestHelper().get("https://pastebin.com/raw/B2sL8QCh")) {
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
        var editCrosshair = new KeyBinding(
            "key.custom-crosshair-mod.edit_crosshair",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_GRAVE_ACCENT,
            "category.custom-crosshair-mod.key_bindings");

        KeyBindingHelper.registerKeyBinding(editCrosshair);

        ClientTickEvents.END_CLIENT_TICK.register(e -> {
            if (this.mc.currentScreen == null && editCrosshair.isPressed())
                this.mc.setScreen(new EditCrosshairGuiScreen(this.properties.getCrosshair()));
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
