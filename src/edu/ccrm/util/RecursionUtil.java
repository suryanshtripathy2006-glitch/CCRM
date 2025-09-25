package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RecursionUtil {

    public static long computeDirectorySize(Path dir) throws IOException {
        if (Files.isRegularFile(dir)) {
            return Files.size(dir);
        }
        long size = 0;
        try (var children = Files.list(dir)) {
            for (Path child : children.toList()) { 
                size += computeDirectorySize(child); 
            }
        }
        return size;
    }
}