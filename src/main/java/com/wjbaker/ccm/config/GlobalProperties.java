package com.wjbaker.ccm.config;

import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.custom.CustomCrosshairDrawer;
import com.wjbaker.ccm.crosshair.property.BooleanProperty;

public final class GlobalProperties {

    private final CustomCrosshair crosshair;
    private final BooleanProperty isModEnabled;
    private final BooleanProperty isLatestVersion;
    private final CustomCrosshairDrawer customCrosshairDrawer;

    public GlobalProperties() {
        this.crosshair = new CustomCrosshair();
        this.isModEnabled = new BooleanProperty("mod_enabled", true);
        this.isLatestVersion = new BooleanProperty("is_latest_version", true);
        this.customCrosshairDrawer = new CustomCrosshairDrawer();
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

    public void setLatestVersion(final boolean isLatestVersion) {
        this.isLatestVersion.set(isLatestVersion);
    }

    public CustomCrosshairDrawer getCustomCrosshairDrawer() {
        return this.customCrosshairDrawer;
    }
}