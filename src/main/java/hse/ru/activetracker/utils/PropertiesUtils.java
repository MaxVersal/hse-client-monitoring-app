package hse.ru.activetracker.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * todo Document type PropertiesUtils
 */
public class PropertiesUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);

    public static Properties readProperties(String filePath) {
        try {
            InputStream input = new FileInputStream(filePath);
            Properties props;
            props = new Properties();
            try {
                props.load(input);
            } catch (Exception e) {
                LOGGER.error("Error during reading producer.properties file");
                throw new RuntimeException(e);
            }
            return props;
        } catch (FileNotFoundException e) {
            LOGGER.error("File producer.properties not found");
            throw new RuntimeException(e);
        }
    }
}
