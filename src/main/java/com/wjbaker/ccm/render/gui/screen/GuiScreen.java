package com.wjbaker.ccm.render.gui.screen;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.helper.ExternalHelper;
import com.wjbaker.ccm.render.ModTheme;
import com.wjbaker.ccm.render.RenderManager;
import com.wjbaker.ccm.render.gui.component.GuiComponent;
import com.wjbaker.ccm.render.gui.component.components.ButtonGuiComponent;
import com.wjbaker.ccm.render.gui.component.event.IOnClickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiScreen extends GuiScreenAdapter {

    protected final RenderManager renderManager;

    protected final GuiScreen parentGuiScreen;
    protected final List<GuiComponent> components;
    protected final int headerHeight;

    private final ButtonGuiComponent newVersionButton;

    public GuiScreen(final String title) {
        this(title, null);
    }

    public GuiScreen(final String title, final GuiScreen parentGuiScreen) {
        super(title);

        this.renderManager = new RenderManager();

        this.parentGuiScreen = parentGuiScreen;
        this.components = new ArrayList<>();
        this.headerHeight = 35;

        this.newVersionButton = new ButtonGuiComponent(this, -1, -1, 125, 25, "New Version Available!");
        this.newVersionButton.setBaseBackgroundColour(ModTheme.TERTIARY);
        this.newVersionButton.setHoverBackgroundColour(ModTheme.TERTIARY_DARK);
        this.newVersionButton.setBaseTextColour(ModTheme.BLACK);
        this.newVersionButton.setHoverTextColour(ModTheme.BLACK);
        this.newVersionButton.addEvent(IOnClickEvent.class, () -> {
            if (!CustomCrosshairMod.INSTANCE.properties().isLatestVersion().get())
                new ExternalHelper().openInBrowser(CustomCrosshairMod.CURSEFORGE_PAGE);
        });
    }

    @Override
    public void update() {
        this.components.forEach(GuiComponent::update);

        this.newVersionButton.setPosition(
            this.width - this.newVersionButton.getWidth() - 5,
            (this.headerHeight / 2) - (this.newVersionButton.getHeight() / 2));
    }

    @Override
    public void draw(final MatrixStack matrixStack) {
        this.renderManager.drawFilledRectangle(matrixStack, 0, 0, this.width, this.height, ModTheme.BLACK.setOpacity(140));

        this.components.forEach(x -> x.draw(matrixStack));

        this.drawHeader(matrixStack);
    }

    private void drawHeader(final MatrixStack matrixStack) {
        this.renderManager.drawFilledRectangle(matrixStack, 0, 0, this.width, this.headerHeight, ModTheme.PRIMARY);
        this.renderManager.drawLine(matrixStack, 0, this.headerHeight, this.width, this.headerHeight, 2.0F, ModTheme.DARK_GREY);

        var titleWidth = this.renderManager.textWidth(CustomCrosshairMod.TITLE);
        var centreY = (this.headerHeight / 2) - (7 / 2);

        this.renderManager.drawText(matrixStack, CustomCrosshairMod.TITLE, 5, centreY, ModTheme.WHITE, true);

        this.renderManager.drawSmallText(
            matrixStack,
            "v" + CustomCrosshairMod.VERSION,
            8 + titleWidth,
            (headerHeight / 2),
            ModTheme.DARK_GREY,
            false);

        if (!CustomCrosshairMod.INSTANCE.properties().isLatestVersion().get())
            this.newVersionButton.draw(matrixStack);
    }

    @Override
    public void onMouseDown(final int mouseX, final int mouseY, final int button) {
        this.components
            .stream()
            .filter(x -> x.isInsideComponent(mouseX, mouseY))
            .forEach(x -> x.onMouseDown(mouseX, mouseY, button));

        this.newVersionButton.onMouseDown(mouseX, mouseY, button);
    }

    @Override
    public void onMouseUp(final int mouseX, final int mouseY, final int button) {
        this.components.forEach(x -> x.onMouseUp(mouseX, mouseY, button));

        this.newVersionButton.onMouseUp(mouseX, mouseY, button);
    }

    @Override
    public void onMouseMove(final int mouseX, int mouseY) {
        this.components.forEach(x -> x.onMouseMove(mouseX, mouseY));

        this.newVersionButton.onMouseMove(mouseX, mouseY);
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
    }
}
