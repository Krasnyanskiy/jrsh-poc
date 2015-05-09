package ua.krasnyanskiy.jrsh.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static boolean createFile(String file, InputStream entity) {
        try {
            File target = new File(file);
            FileUtils.copyInputStreamToFile(entity, target);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
