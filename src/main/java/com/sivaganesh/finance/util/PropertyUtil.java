package com.sivaganesh.finance.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = PropertyUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties");
            }
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
