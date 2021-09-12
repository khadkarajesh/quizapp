package com.epita;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public enum Configuration {
    INSTANCE;
    Properties properties;
    private static final String DB_HOST = "db.host";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";

    Configuration() {
        this.properties = new Properties();
        try {
            properties.load(new FileInputStream("conf.properties"));
        } catch (FileNotFoundException e) {
            System.out.println("Please provide your configuration file to get started in the root directory");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return properties.getProperty(DB_HOST);
    }

    public String getUser() {
        return properties.getProperty(DB_USER);
    }

    public String getPassword() {
        return properties.getProperty(DB_PASSWORD);
    }
}
