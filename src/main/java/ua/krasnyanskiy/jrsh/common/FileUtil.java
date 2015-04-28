package ua.krasnyanskiy.jrsh.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static sun.misc.IOUtils.readFully;

public class FileUtil {

    public static boolean createFile(String file, InputStream entity) {
        try {
            new FileOutputStream(file).write(readFully(entity, -1, false));
            return true;
        } catch (IOException e) {
            return false; // Cannot create a file
        }
    }

}
