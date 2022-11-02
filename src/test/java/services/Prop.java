package services;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class Prop {
    private static final String rs_ex = "src/test/resources/ex.properties";
    private static final String rs_elements = "src/test/resources/elements.properties";

    public static Properties loadProperties(String path){
        Charset inputCharset = StandardCharsets.UTF_8;

        Properties properties = new Properties();
        FileInputStream file = null;
        try {
            file = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void writeProperties(String propKey, String propValue) {
        Properties properties = new Properties();
        File file = new File(rs_ex);

        try (InputStream in = Files.newInputStream(file.toPath()))
        {
            properties.load(in);
            properties.setProperty(propKey, propValue);

            OutputStream out = Files.newOutputStream(file.toPath());
            properties.store(out, null);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        properties.stringPropertyNames().stream()
                .map(key -> key + ":" + properties.getProperty(key))
                .forEach(System.out::println);
    }

    public static Properties elements(){
        return loadProperties(rs_elements);
    }

}
