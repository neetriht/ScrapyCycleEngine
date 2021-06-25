package com.scen.datastr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by neetriht on 2017-06-10.
 */
public class Utility {

    public final static Properties applicationProperties = new Properties();

    public void loadProperties() {
        try {
            InputStream is = Utility.class.getClassLoader().getResourceAsStream("application.properties");
            if (is != null) {
                applicationProperties.load(is);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
