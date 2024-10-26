package com.wjbaker.ccm.config;

import com.wjbaker.ccm.CustomCrosshairMod;
import com.wjbaker.ccm.crosshair.CustomCrosshair;
import com.wjbaker.ccm.crosshair.types.ICrosshairProperty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final CustomCrosshair crosshair;
    private final ICrosshairProperty<?>[] extraProperties;

    public ConfigManager(
        final String fileName,
        final CustomCrosshair crosshair,
        final ICrosshairProperty<?>... extraProperties) {

        this.fileName = fileName;
        this.crosshair = crosshair;
        this.extraProperties = extraProperties;
    }

    public boolean read() {
        try (final var reader = new BufferedReader(new FileReader(this.fileName))) {
            String currentLine;

            CustomCrosshairMod.INSTANCE.log("Config Manager (Read)", "Started reading file '{}'.", this.fileName);

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith(this.comment))
                    continue;

                var lineSplit = currentLine.split(":");

                if (lineSplit.length == 0)
                    continue;

                var alias = lineSplit[0].trim().toLowerCase();
                var value = lineSplit[1].trim().toLowerCase();

                var property = this.getAllProperties().get(alias);
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
            CustomCrosshairMod.INSTANCE.log("Config Manager (Read)", "Failed reading file '{}'.", this.fileName);
            return false;
        }
    }

    public boolean write() {
        try (final var writer = new BufferedWriter(new FileWriter(this.fileName))) {
            CustomCrosshairMod.INSTANCE.log("Config Manager (Write)", "Start writing file '{}'.", this.fileName);

            for (var line : this.header) {
                writer.write(line);
                writer.newLine();
            }

            for (var property : this.getAllProperties().values()) {
                writer.write(String.format("%s:%s", property.alias(), property.forConfig()));
                writer.newLine();
            }

            CustomCrosshairMod.INSTANCE.log("Config Manager (Write)", "Finished writing file '{}'.", this.fileName);

            return true;
        }
        catch (final Exception e) {
            CustomCrosshairMod.INSTANCE.log("Config Manager (Write)", "Failed writing file '{}'.", this.fileName);
            return false;
        }
    }

    private Map<String, ICrosshairProperty<?>> getAllProperties() {
        var properties = new ArrayList<ICrosshairProperty<?>>();
        properties.addAll(this.crosshair.propertiesAsList);
        properties.addAll(Arrays.stream(this.extraProperties).toList());

        return properties
            .stream()
            .collect(Collectors.toMap(ICrosshairProperty::alias, p -> p));
    }
}
