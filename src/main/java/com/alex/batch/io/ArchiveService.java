package com.alex.batch.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;

public final class ArchiveService {

    public Path archive(Path inputFile, Path archiveDir) throws IOException {
        String name = inputFile.getFileName().toString();
        String stamped = name + "." + Instant.now().toEpochMilli();
        Path dest = archiveDir.resolve(stamped);

        return Files.move(inputFile, dest, StandardCopyOption.REPLACE_EXISTING);
    }
}