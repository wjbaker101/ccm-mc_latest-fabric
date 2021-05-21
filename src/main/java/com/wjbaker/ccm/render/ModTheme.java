package com.wjbaker.ccm.render;

import com.wjbaker.ccm.type.RGBA;

public abstract class ModTheme {

    public static final RGBA PRIMARY = new RGBA(84, 107, 150, 255);
    public static final RGBA SECONDARY = new RGBA(45, 73, 125, 255);

    public static final RGBA TERTIARY = new RGBA(255, 180, 0, 255);
    public static final RGBA TERTIARY_DARK = new RGBA(198, 142, 11, 255);

    public static final RGBA LIGHT_GREY = new RGBA(248, 249, 251, 255);
    public static final RGBA DARK_GREY = new RGBA(235, 238, 244, 255);
    public static final RGBA WHITE = new RGBA(255, 255, 255, 255);
    public static final RGBA BLACK = new RGBA(32, 32, 32, 255);

    public static final RGBA TRANSPARENT = new RGBA(0, 0, 0, 0);

    public static final RGBA SUCCESS = new RGBA(86, 169, 108, 255);
    public static final RGBA ERROR = new RGBA(169, 86, 108, 255);

    private ModTheme() {}
}
