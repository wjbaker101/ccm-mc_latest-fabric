package com.wjbaker.ccm.gui.component.components;

import com.wjbaker.ccm.gui.component.GuiComponent;
import com.wjbaker.ccm.gui.screen.GuiScreen;
import com.wjbaker.ccm.gui.type.GuiBounds;
import com.wjbaker.ccm.rendering.ModTheme;
import net.minecraft.client.gui.DrawContext;

public final class ScrollPanelGuiComponent extends PanelGuiComponent {

    private final int scrollBarWidth;
    private final int scrollBarSpeed;
    private final int scrollBarSize;

    private boolean isScrollBarDragging;
    private int scrollBarPosition;
    private int grabOffset;
    private int contentHeight;

    public ScrollPanelGuiComponent(
        final GuiScreen parentGuiScreen,
        final int x,
        final int y,
        final int width,
        final int height) {

        super(parentGuiScreen, x, y, width, height);

        this.scrollBarWidth = 9;
        this.scrollBarSpeed = 15;
        this.scrollBarSize = 30;

        this.isScrollBarDragging = false;
        this.scrollBarPosition = 0;
        this.grabOffset = 0;

        this.baseBackgroundColour = ModTheme.TRANSPARENT;
        this.hoverBackgroundColour = this.baseBackgroundColour;
        this.currentBackgroundColour = this.baseBackgroundColour;

        this.isBorderVisible = false;
        this.contentHeight = 0;
        this.isScissoringEnabled = true;
    }

    @Override
    public void draw(final DrawContext drawContext) {
        super.draw(drawContext);

        if (this.contentHeight < this.height)
            return;

        var matrixStack = drawContext.getMatrices();

        this.renderManager.drawBorderedRectangle(
            matrixStack,
            this.x + this.width - this.scrollBarWidth, this.y + 1,
            this.x + this.width, this.y + this.height - 1,
            2.0F,
            this.currentBorderColour,
            this.currentBackgroundColour);

        this.renderManager.drawBorderedRectangle(
            matrixStack,
            this.x + this.width - this.scrollBarWidth, this.y + 1 + this.scrollBarPosition,
            this.x + this.width, this.y + this.scrollBarPosition + this.scrollBarSize,
            2.0F,
            ModTheme.DARK_GREY,
            this.isScrollBarDragging ? ModTheme.SECONDARY : ModTheme.PRIMARY);

        var thumbCentreX = this.x + this.width - (this.scrollBarWidth / 2.0F);

        this.renderManager.drawLines(matrixStack, new float[] {
            thumbCentreX - 2, this.y + this.scrollBarPosition + 6,
            thumbCentreX, this.y + this.scrollBarPosition + 4,
            thumbCentreX, this.y + this.scrollBarPosition + 4,
            thumbCentreX + 2, this.y + this.scrollBarPosition + 6
        }, 2.0F, ModTheme.WHITE);

        this.renderManager.drawLines(matrixStack, new float[] {
            thumbCentreX - 2, this.y + this.scrollBarPosition + this.scrollBarSize - 5,
            thumbCentreX, this.y + this.scrollBarPosition + this.scrollBarSize - 3,
            thumbCentreX, this.y + this.scrollBarPosition + this.scrollBarSize - 3,
            thumbCentreX + 2, this.y + this.scrollBarPosition + this.scrollBarSize - 5
        }, 2.0F, ModTheme.WHITE);
    }

    @Override
    protected GuiBounds bounds() {
        return new GuiBounds(this.x, this.y, this.width - this.scrollBarWidth, this.height);
    }

    @Override
    public void onMouseDown(final int mouseX, final int mouseY, final int button) {
        super.onMouseDown(mouseX, mouseY, button);

        if (this.isInsideScrollBarThumb(mouseX, mouseY)) {
            this.isScrollBarDragging = true;
            this.grabOffset = mouseY - (this.y + this.scrollBarPosition);
        }
    }

    private boolean isInsideScrollBarThumb(final int mouseX, final int mouseY) {
        return mouseX > this.x + this.width - this.scrollBarWidth
            && mouseX < this.x + this.width
            && mouseY > this.y + 1 + this.scrollBarPosition
            && mouseY < this.y + this.scrollBarPosition + this.scrollBarSize;
    }

    @Override
    public void onMouseMove(final int mouseX, final int mouseY) {
        super.onMouseMove(mouseX, mouseY);
    }

    @Override
    public void onMouseUp(final int mouseX, final int mouseY, final int button) {
        super.onMouseUp(mouseX, mouseY, button);

        this.isScrollBarDragging = false;
    }

    @Override
    public void onMouseDrag(final int startX, final int startY, final int mouseX, final int mouseY) {
        super.onMouseDrag(startX, startY, mouseX, mouseY);

        if (this.isScrollBarDragging) {
            var minY = 0;
            var maxY = this.height - 1 - this.scrollBarSize;
            var newPosition = mouseY - this.grabOffset - this.y;

            this.scrollBarPosition = Math.min(maxY, Math.max(minY, newPosition));

            this.onScroll();
        }
    }

    @Override
    public void onMouseScrollUp() {
        super.onMouseScrollUp();

        this.scrollBarPosition = Math.max(0, this.scrollBarPosition - this.scrollBarSpeed);

        this.onScroll();
    }

    @Override
    public void onMouseScrollDown() {
        super.onMouseScrollDown();

        this.scrollBarPosition = Math.min(
            this.height - 1 - this.scrollBarSize,
            this.scrollBarPosition + this.scrollBarSpeed);

        this.onScroll();
    }

    private void onScroll() {
        var height = this.padding;

        var remainingContentHeight = this.contentHeight + this.padding - this.height;
        var ratio = this.scrollBarPosition / (float)(this.height - this.scrollBarSize);

        for (GuiComponent component : this.components) {
            component.setPosition(this.x + this.padding, this.y + height - (int)(remainingContentHeight * ratio));

            height += component.getHeight() + this.componentSpacing;
        }
    }

    @Override
    public void pack() {
        int height = this.padding;

        for (int index = 0; index < this.components.size(); ++index) {
            var component = this.components.get(index);

            component.setPosition(this.x + this.padding, this.y + height);

            height += component.getHeight();

            if (index != this.components.size() - 1)
                height += this.componentSpacing;
        }

        this.contentHeight = this.calculateContentHeight();
    }

    private int calculateContentHeight() {
        var height = this.components
            .stream()
            .mapToInt(x -> x.getHeight() + this.componentSpacing)
            .sum();

        if (!this.components.isEmpty())
            height -= this.componentSpacing;

        return height + this.padding + this.padding;
    }
}