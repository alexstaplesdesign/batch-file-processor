package com.alex.batch.io;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileDiscoverer {
    public List<Path> discover(Path inputDir, String globPattern) throws IOException {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + globPattern);

        try (Stream<Path> stream = Files.list(inputDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(p -> matcher.matches(p.getFileName()))
                    .sorted(Comparator.comparing(p -> p.getFileName().toString()))
                    .collect(Collectors.toList());
        }
    }
}