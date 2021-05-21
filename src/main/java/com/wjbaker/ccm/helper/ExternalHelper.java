package com.wjbaker.ccm.helper;

import net.minecraft.util.Util;

public final class ExternalHelper {

    public void openInBrowser(final String url) {
        try {
            Util.getOperatingSystem().open(url);
        }
        catch (final Exception ignored) {}
    }
}
