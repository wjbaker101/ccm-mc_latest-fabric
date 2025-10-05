package com.wjbaker.ccm.gui.screen;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
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
    public boolean mouseClicked(final Click click, final boolean doubled) {
        this.onMouseDown((int)click.x(), (int)click.y(), click.button());
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(final Click click) {
        this.onMouseUp((int)click.x(), (int)click.y(), click.button());
        return super.mouseReleased(click);
    }

    @Override
    public void mouseMoved(final double mouseX, final double mouseY) {
        this.onMouseMove((int)mouseX, (int)mouseY);
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
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
    public boolean keyPressed(final KeyInput input) {
        this.onKeyDown(input.getKeycode());
        return super.keyPressed(input);
    }

    @Override
    public boolean keyReleased(final KeyInput input) {
        this.onKeyUp(input.getKeycode());
        return super.keyReleased(input);
    }
}