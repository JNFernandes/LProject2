package app.serialization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    /**
     *
     * @param basePath
     * @param fileName
     * @return
     */
    public static boolean fileExists(String basePath, String fileName)
    {
        return Files.exists(Path.of(basePath + "/" + fileName));
    }

    public static boolean deleteFile(String basePath, String fileName) throws IOException
    {
        return Files.deleteIfExists(Path.of(basePath + "/" + fileName));
    }

}
