package com.wjbaker.ccm.render.gui.component;

import com.wjbaker.ccm.render.RenderManager;
import com.wjbaker.ccm.render.gui.component.event.IGuiComponentEvent;
import com.wjbaker.ccm.render.gui.event.IMouseEvents;
import com.wjbaker.ccm.render.gui.screen.GuiScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GuiComponent extends GuiComponentWithComponents implements IMouseEvents {

    protected final RenderManager renderManager;

    protected final GuiScreen parentGuiScreen;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    private final Map<Class<? extends IGuiComponentEvent>, List<IGuiComponentEvent>> events;
    protected long mouseDownDuration;
    protected boolean isMouseDown;
    protected boolean isMouseDownInside;
    private boolean isMouseOver;


    public GuiComponent(final GuiScreen parentGuiScreen, final int x, final int y, final int width, final int height) {
        this.renderManager = new RenderManager();

        this.parentGuiScreen = parentGuiScreen;
        this.events = new HashMap<>();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.mouseDownDuration = 0L;
        this.isMouseDown = false;
        this.isMouseDownInside = false;
        this.isMouseOver = false;
    }

    public void update() {
        if (this.isMouseDown)
            ++this.mouseDownDuration;
    }

    @Override
    public void onMouseDown(final int mouseX, final int mouseY, final int button) {
        super.onMouseDown(mouseX, mouseY, button);

        this.isMouseDown = true;
    }

    @Override
    public void onMouseUp(final int mouseX, final int mouseY, final int button) {
        super.onMouseUp(mouseX, mouseY, button);

        this.isMouseDown = false;
    }

    @Override
    public void onMouseMove(final int mouseX, final int mouseY) {
        super.onMouseMove(mouseX, mouseY);

        boolean prevIsMouseOver = this.isMouseOver;
        this.isMouseOver = this.isInsideComponent(mouseX, mouseY);

        if (this.isMouseOver == prevIsMouseOver)
            return;

        if (this.isMouseOver) {
            this.currentBackgroundColour = this.hoverBackgroundColour;
            this.currentBorderColour = this.hoverBorderColour;
            this.currentTextColour = this.hoverTextColour;
        }
        else {
            this.currentBackgroundColour = this.baseBackgroundColour;
            this.currentBorderColour = this.baseBorderColour;
            this.currentTextColour = this.baseTextColour;
        }
    }

    public boolean isInsideComponent(final int x, final int y) {
        return x > this.x && x <= this.x + this.width
            && y > this.y && y <= this.y + this.height;
    }

    public void setPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public <TEvent extends IGuiComponentEvent> void addEvent(final Class<TEvent> type, final TEvent event) {
        if (!this.events.containsKey(type))
            this.events.put(type, new ArrayList<>());

        this.events.get(type).add(event);
    }

    public <TEvent extends IGuiComponentEvent> void removeEvent(final Class<TEvent> type, final TEvent event) {
        if (this.events.containsKey(type))
            this.events.get(type).remove(event);
    }

    protected <TEvent extends IGuiComponentEvent> List<IGuiComponentEvent> events(final Class<TEvent> type) {
        return this.events.getOrDefault(type, new ArrayList<>());
    }
}
