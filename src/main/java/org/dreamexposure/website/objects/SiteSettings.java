package org.dreamexposure.website.objects;

import java.util.Properties;

public enum SiteSettings {
    PORT;

    private String val;

    SiteSettings() {
    }

    public static void init(Properties properties) {
        for (SiteSettings s : values()) {
            s.set(properties.getProperty(s.name()));
        }
    }

    public String get() {
        return val;
    }

    public void set(String val) {
        this.val = val;
    }
}
