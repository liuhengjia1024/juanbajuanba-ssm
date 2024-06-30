package com.liuhengjia.util;

import java.io.File;

public class FileUtil {
    private FileUtil() {
    }

    public static File createSaveDir(String rootPath, String[] dirNames) {
        StringBuilder saveDriPath = new StringBuilder(rootPath);

        for (int i = 0; i < dirNames.length; i++) {
            String dirName = dirNames[i];
            saveDriPath.append(File.separator).append(dirName);
        }

        return new File(saveDriPath.toString());
    }

    public static File getFileSavePath(File saveDir, String fileName) {
        return new File("" + saveDir + File.separator + fileName);
    }
}
