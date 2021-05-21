package com.wjbaker.ccm.config;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.property.ICrosshairProperty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

public final class ConfigManager {

    private final String comment = "#";
    private final String[] header = new String[] {
            String.format("%s %s v%s", comment, CustomCrosshairMod.TITLE, CustomCrosshairMod.VERSION),
            String.format("%s Made by Sparkless101", comment),
            String.format("%s ------------------------------------", comment),
            String.format("%s %s", comment, CustomCrosshairMod.MC_FORUMS_PAGE),
            String.format("%s %s", comment, CustomCrosshairMod.CURSEFORGE_PAGE),
            String.format("%s ------------------------------------", comment),
            String.format("%s This config file contains the properties of the crosshair and mod properties. Feel free to edit them.", comment),
            String.format("%s ------------------------------------", comment),
    };

    private final String fileName;
    private final Map<String, ICrosshairProperty<?>> properties;

    public ConfigManager(final String fileName, Map<String, ICrosshairProperty<?>> properties) {
        this.fileName = fileName;
        this.properties = properties;
    }

    public boolean read() {
        try (final BufferedReader reader = new BufferedReader(new FileReader(this.fileName))) {
            String currentLine;

            CustomCrosshairMod.INSTANCE.log("Config Manager (Read)", "Started reading file '{}'.", this.fileName);

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith(this.comment))
                    continue;

                String[] lineSplit = currentLine.split(":");

                if (lineSplit.length == 0)
                    continue;

                String alias = lineSplit[0].trim().toLowerCase();
                String value = lineSplit[1].trim().toLowerCase();

                ICrosshairProperty<?> property = this.properties.get(alias);

                if (property == null)
                    continue;

                property.setFromConfig(value);

                CustomCrosshairMod.INSTANCE.log(
                    "Config Manager (Read)",
                    "Set property '{}' to '{}'.", property.alias(), property.get());
            }

            CustomCrosshairMod.INSTANCE.log("Config Manager (Read)", "Finished reading file '{}'.", this.fileName);

            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    public boolean write() {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName))) {
            CustomCrosshairMod.INSTANCE.log("Config Manager (Write)", "Start writing file '{}'.", this.fileName);

            for (String line : this.header) {
                writer.write(line);
                writer.newLine();
            }

            for (ICrosshairProperty<?> property : this.properties.values()) {
                writer.write(String.format("%s:%s", property.alias(), property.forConfig()));
                writer.newLine();
            }

            CustomCrosshairMod.INSTANCE.log("Config Manager (Write)", "Finished writing file '{}'.", this.fileName);

            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }
}
