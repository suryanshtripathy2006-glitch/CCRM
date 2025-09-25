package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {

    private final Path backupRoot = Paths.get("backups");
    private final Path dataPath = Paths.get("data");

    public void backup() throws IOException {
        if (!Files.exists(backupRoot)) {
            Files.createDirectories(backupRoot);
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        Path backupDir = backupRoot.resolve(timestamp);
        Files.createDirectories(backupDir);

        try (var walker = Files.walk(dataPath)) {
            walker.forEach(source -> {
                try {
                    Path dest = backupDir.resolve(dataPath.relativize(source));
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(dest);
                    } else {
                        Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}