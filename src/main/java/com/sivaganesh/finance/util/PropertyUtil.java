package com.sivaganesh.finance.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    public static Properties loadProperties(String fileName) {
        Properties props = new Properties();
        try (InputStream input = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("Unable to find " + fileName);
                return null;
            }
            props.load(input);
        } catch (Exception e) {
            System.out.println("Error loading properties file: " + e.getMessage());
        }
        return props;
    }
}
