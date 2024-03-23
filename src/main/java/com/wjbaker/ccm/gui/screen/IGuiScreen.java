package com.wjbaker.ccm.gui.screen;

import com.wjbaker.ccm.gui.event.IKeyboardEvents;
import com.wjbaker.ccm.gui.event.IMouseEvents;
import net.minecraft.client.gui.DrawContext;

public interface IGuiScreen extends IMouseEvents, IKeyboardEvents {

    void update();
    void close();

    void draw(final DrawContext drawContext);
}
