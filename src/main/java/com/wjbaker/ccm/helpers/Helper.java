package com.wjbaker.ccm.helpers;

import net.minecraft.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Helper {

    private Helper() {}

    public static void openInBrowser(final String url) {
        try {
            Util.getOperatingSystem().open(url);
        }
        catch (final Exception ignored) {}
    }

    public static BufferedReader getUrl(final String request) throws IOException {
        var url = new URL(request);

        var connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.91 Safari/537.36");

        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }
}