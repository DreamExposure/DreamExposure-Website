package org.dreamexposure.website;

import org.dreamexposure.website.account.AccountHandler;
import org.dreamexposure.website.database.DatabaseManager;
import org.dreamexposure.website.objects.SiteSettings;
import org.dreamexposure.website.utils.EmailHandler;
import org.dreamexposure.website.utils.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException {
        //Load settings
        Properties p = new Properties();
        p.load(new FileReader(new File("settings.properties")));
        SiteSettings.init(p);

        Logger.getLogger().init();

        //Init database
        DatabaseManager.getManager().connectToMySQL();
        DatabaseManager.getManager().createTables();

        //Init spark
        AccountHandler.getHandler().init();
        SpringApplication.run(Main.class, args);

        //Init the rest of our services
        EmailHandler.getHandler().init();
    }
}