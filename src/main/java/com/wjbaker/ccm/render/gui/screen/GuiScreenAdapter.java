package com.wjbaker.ccm.render.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public abstract class GuiScreenAdapter extends Screen implements IGuiScreen {

    protected GuiScreenAdapter(final String title) {
        super(Text.of(title));
    }

    @Override
    public void tick() {
        this.update();
        super.tick();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void render(final DrawContext drawContext, final int mouseX, final int mouseY, final float delta) {
        super.render(drawContext, mouseX, mouseY, delta);
        this.draw(drawContext);
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        this.onMouseDown((int)mouseX, (int)mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(final double mouseX, final double mouseY, final int button) {
        this.onMouseUp((int)mouseX, (int)mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(final double mouseX, final double mouseY) {
        this.onMouseMove((int)mouseX, (int)mouseY);
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseDragged(
        final double mouseX,
        final double mouseY,
        final int button,
        final double deltaX,
        final double deltaY) {

        this.onMouseDrag((int)mouseX, (int)mouseY, (int)(mouseX + deltaX), (int)(mouseY + deltaY));
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
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
    public boolean keyPressed(final int keyCode, final int scanCode, final int modifiers) {
        this.onKeyDown(keyCode);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(final int keyCode, final int scanCode, final int modifiers) {
        this.onKeyUp(keyCode);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }
}
