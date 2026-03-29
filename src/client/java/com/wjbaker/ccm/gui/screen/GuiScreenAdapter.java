package com.wjbaker.ccm.gui.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public abstract class GuiScreenAdapter extends Screen implements IGuiScreen {

    protected GuiScreenAdapter(final String title) {
        super(Component.literal(title));
    }

    @Override
    public void tick() {
        this.update();
        super.tick();
    }

    @Override
    public void close() {
        super.onClose();
    }

    @Override
    public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        this.draw(graphics);
    }

    @Override
    public boolean mouseClicked(final MouseButtonEvent click, final boolean doubled) {
        this.onMouseDown((int)click.x(), (int)click.y(), click.button());
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(final MouseButtonEvent click) {
        this.onMouseUp((int)click.x(), (int)click.y(), click.button());
        return super.mouseReleased(click);
    }

    @Override
    public void mouseMoved(final double mouseX, final double mouseY) {
        this.onMouseMove((int)mouseX, (int)mouseY);
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseDragged(final MouseButtonEvent click, double offsetX, double offsetY) {
        this.onMouseDrag((int)click.x(), (int)click.y(), (int)(click.x() + offsetX), (int)(click.y() + offsetY));
        return super.mouseDragged(click, offsetX, offsetY);
    }

    @Override
    public boolean mouseScrolled(final double mouseX, final double mouseY, final double horizontalAmount, final double verticalAmount) {
        if (verticalAmount > 0)
            this.onMouseScrollUp();
        else
            this.onMouseScrollDown();

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(final KeyEvent input) {
        this.onKeyDown(input.key());
        return super.keyPressed(input);
    }

    @Override
    public boolean keyReleased(final KeyEvent input) {
        this.onKeyUp(input.key());
        return super.keyReleased(input);
    }
}