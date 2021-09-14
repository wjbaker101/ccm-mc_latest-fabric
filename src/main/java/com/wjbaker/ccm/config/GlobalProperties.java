package com.wjbaker.ccm.config;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.property.BooleanProperty;
import com.wjbaker.ccm.crosshair.property.RGBAProperty;
import com.wjbaker.ccm.type.RGBA;

public final class GlobalProperties {

    private final CustomCrosshair crosshair;
    private final BooleanProperty isModEnabled;
    private final BooleanProperty isLatestVersion;
    private final RGBAProperty blockOutlineColour;

    public GlobalProperties() {
        this.crosshair = new CustomCrosshair();
        this.isModEnabled = new BooleanProperty("mod_enabled", true);
        this.isLatestVersion = new BooleanProperty("is_latest_version", true);
        this.blockOutlineColour = new RGBAProperty("block_outline_colour", new RGBA(0, 0, 0, 0));
    }

    public CustomCrosshair getCrosshair() {
        return this.crosshair;
    }

    public BooleanProperty getIsModEnabled() {
        return this.isModEnabled;
    }

    public BooleanProperty isLatestVersion() {
        return this.isLatestVersion;
    }

    public RGBAProperty getBlockOutlineColour() {
        return this.blockOutlineColour;
    }

    public void setLatestVersion(final boolean isLatestVersion) {
        this.isLatestVersion.set(isLatestVersion);
    }
}
