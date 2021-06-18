package com.wjbaker.ccm.render.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public abstract class GuiScreenAdapter extends Screen implements IGuiScreen {

    protected GuiScreenAdapter(final String title) {
        super(new LiteralText(title));
    }

    @Override
    public void tick() {
        this.update();
        super.tick();
    }

    @Override
    public void onClose() {
        this.close();
        super.onClose();
    }

    @Override
    public void render(final MatrixStack matrixStack, final int mouseX, final int mouseY, final float delta) {
        this.draw(matrixStack);
        super.render(matrixStack, mouseX, mouseY, delta);
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
    public boolean mouseScrolled(final double mouseX, final double mouseY, final double amount) {
        if (amount > 0)
            this.onMouseScrollUp();
        else
            this.onMouseScrollDown();

        return super.mouseScrolled(mouseX, mouseY, amount);
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
