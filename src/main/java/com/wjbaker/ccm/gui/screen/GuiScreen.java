package com.wjbaker.ccm.gui.screen;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.gui.component.GuiComponent;
import com.wjbaker.ccm.gui.component.components.ButtonGuiComponent;
import com.wjbaker.ccm.gui.component.event.IOnClickEvent;
import com.wjbaker.ccm.helpers.Helper;
import com.wjbaker.ccm.rendering.ModTheme;
import com.wjbaker.ccm.rendering.RenderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiScreen extends GuiScreenAdapter {

    protected final RenderManager renderManager;

    protected final GuiScreen parentGuiScreen;
    protected final List<GuiComponent> components;
    protected final int headerHeight;

    private final ButtonGuiComponent newVersionButton;
    private final ButtonGuiComponent patreonButton;
    private final ButtonGuiComponent paypalButton;

    public GuiScreen(final String title) {
        this(title, null);
    }

    public GuiScreen(final String title, final GuiScreen parentGuiScreen) {
        super(title);

        this.renderManager = new RenderManager();

        this.parentGuiScreen = parentGuiScreen;
        this.components = new ArrayList<>();
        this.headerHeight = 35;

        this.newVersionButton = new ButtonGuiComponent(this, -1, -1, 125, 25, I18n.translate("custom_crosshair_mod.screen.new_version"));
        this.newVersionButton.setBaseBackgroundColour(ModTheme.TERTIARY);
        this.newVersionButton.setHoverBackgroundColour(ModTheme.TERTIARY_DARK);
        this.newVersionButton.setBaseTextColour(ModTheme.BLACK);
        this.newVersionButton.setHoverTextColour(ModTheme.BLACK);
        this.newVersionButton.addEvent(IOnClickEvent.class, () -> {
            if (!CustomCrosshairMod.INSTANCE.properties().isLatestVersion().get())
                Helper.openInBrowser(CustomCrosshairMod.CURSEFORGE_PAGE);
        });

        this.patreonButton = new ButtonGuiComponent(this, -1, -1, 125, 25, I18n.translate("custom_crosshair_mod.screen.support_on_patreon"));
        this.patreonButton.setBaseBackgroundColour(ModTheme.SECONDARY);
        this.patreonButton.setHoverBackgroundColour(ModTheme.PRIMARY);
        this.patreonButton.setBaseTextColour(ModTheme.WHITE);
        this.patreonButton.setHoverTextColour(ModTheme.WHITE);
        this.patreonButton.addEvent(IOnClickEvent.class, () -> {
            Helper.openInBrowser(CustomCrosshairMod.PATREON_PAGE);
        });

        this.paypalButton = new ButtonGuiComponent(this, -1, -1, 125, 25, I18n.translate("custom_crosshair_mod.screen.support_on_paypal"));
        this.paypalButton.setBaseBackgroundColour(ModTheme.SECONDARY);
        this.paypalButton.setHoverBackgroundColour(ModTheme.PRIMARY);
        this.paypalButton.setBaseTextColour(ModTheme.WHITE);
        this.paypalButton.setHoverTextColour(ModTheme.WHITE);
        this.paypalButton.addEvent(IOnClickEvent.class, () -> {
            Helper.openInBrowser(CustomCrosshairMod.PAYPAL_PAGE);
        });
    }

    @Override
    public void update() {
        this.components.forEach(GuiComponent::update);

        var x = this.width;

        x -= this.newVersionButton.getWidth() + 5;

        this.newVersionButton.setPosition(
            x,
            (this.headerHeight / 2) - (this.newVersionButton.getHeight() / 2));

        x -= this.patreonButton.getWidth() + 5;

        this.patreonButton.setPosition(
            x,
            (this.headerHeight / 2) - (this.patreonButton.getHeight() / 2));

        x -= this.paypalButton.getWidth() + 5;

        this.paypalButton.setPosition(
            x,
            (this.headerHeight / 2) - (this.paypalButton.getHeight() / 2));
    }

    @Override
    public void draw(final DrawContext drawContext) {
        var matrixStack = drawContext.getMatrices();

        this.renderManager.drawFilledRectangle(matrixStack, 0, 0, this.width, this.height, ModTheme.BLACK.setOpacity(140));

        this.components.forEach(x -> x.draw(drawContext));

        this.drawHeader(drawContext);
    }

    private void drawHeader(final DrawContext drawContext) {
        var matrixStack = drawContext.getMatrices();

        this.renderManager.drawFilledRectangle(matrixStack, 0, 0, this.width, this.headerHeight, ModTheme.PRIMARY);
        this.renderManager.drawLine(matrixStack, 0, this.headerHeight, this.width, this.headerHeight, 2.0F, ModTheme.DARK_GREY);

        var titleWidth = this.renderManager.textWidth(CustomCrosshairMod.TITLE);
        var centreY = (this.headerHeight / 2) - (7 / 2);

        this.renderManager.drawText(drawContext, CustomCrosshairMod.TITLE, 5, centreY, ModTheme.WHITE, true);

        this.renderManager.drawSmallText(
            drawContext,
            "v" + CustomCrosshairMod.VERSION,
            8 + titleWidth,
            (headerHeight / 2),
            ModTheme.DARK_GREY,
            false);

        if (!CustomCrosshairMod.INSTANCE.properties().isLatestVersion().get())
            this.newVersionButton.draw(drawContext);

        this.patreonButton.draw(drawContext);
        this.paypalButton.draw(drawContext);
    }

    @Override
    public void onMouseDown(final int mouseX, final int mouseY, final int button) {
        this.components
            .stream()
            .filter(x -> x.isInsideComponent(mouseX, mouseY))
            .forEach(x -> x.onMouseDown(mouseX, mouseY, button));

        this.newVersionButton.onMouseDown(mouseX, mouseY, button);
        this.patreonButton.onMouseDown(mouseX, mouseY, button);
        this.paypalButton.onMouseDown(mouseX, mouseY, button);
    }

    @Override
    public void onMouseUp(final int mouseX, final int mouseY, final int button) {
        this.components.forEach(x -> x.onMouseUp(mouseX, mouseY, button));

        this.newVersionButton.onMouseUp(mouseX, mouseY, button);
        this.patreonButton.onMouseUp(mouseX, mouseY, button);
        this.paypalButton.onMouseUp(mouseX, mouseY, button);
    }

    @Override
    public void onMouseMove(final int mouseX, int mouseY) {
        this.components.forEach(x -> x.onMouseMove(mouseX, mouseY));

        this.newVersionButton.onMouseMove(mouseX, mouseY);
        this.patreonButton.onMouseMove(mouseX, mouseY);
        this.paypalButton.onMouseMove(mouseX, mouseY);
    }

    @Override
    public void onMouseDrag(final int startX, final int startY, final int mouseX, final int mouseY) {
        this.components.forEach(x -> x.onMouseDrag(startX, startY, mouseX, mouseY));
    }

    @Override
    public void onMouseScrollUp() {
        this.components.forEach(GuiComponent::onMouseScrollUp);
    }

    @Override
    public void onMouseScrollDown() {
        this.components.forEach(GuiComponent::onMouseScrollDown);
    }

    @Override
    public void onKeyDown(final int keyCode) {
        if (keyCode == 256 && this.parentGuiScreen != null)
            MinecraftClient.getInstance().setScreen(this.parentGuiScreen);
    }

    @Override
    public void onKeyUp(final int keyCode) {}

    @Override
    public void close() {
        CustomCrosshairMod.INSTANCE.configManager().write();
        super.close();
    }
}