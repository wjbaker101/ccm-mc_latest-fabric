package com.wjbaker.ccm.render.gui.event;

public interface IMouseEvents {

    void onMouseUp(final int x, final int y, final int button);
    void onMouseDown(final int x, final int y, final int button);
    void onMouseMove(final int x, final int y);
    void onMouseDrag(final int startX, final int startY, final int x, final int y);
    void onMouseScrollUp();
    void onMouseScrollDown();
}
