package org.dreamexposure.website;

import org.dreamexposure.website.objects.SiteSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException {
        //Load settings
        Properties p = new Properties();
        p.load(new FileReader("settings.properties"));
        SiteSettings.init(p);

        //Init spring
        SpringApplication.run(Main.class, args);
    }
}
