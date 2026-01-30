package com.alex.batch.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class ArgsParser {
    private ArgsParser() { }

    public static Args parse(String[] args) {
        Map<String, String> map = toMap(args);

        Path input = getPath(map, "--input");
        Path output = getPath(map, "--output");
        Path archive = getPath(map, "--archive");

        String pattern = map.getOrDefault("--pattern", "*.csv");
        boolean failOnError = Boolean.parseBoolean(map.getOrDefault("--failOnError", "false"));

        validateDirectories(input, output, archive);

        return new Args(input, output, archive, pattern, failOnError);
    }

    private static Map<String, String> toMap(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String key = args[i];
            if (!key.startsWith("--")) {
                throw new IllegalArgumentException("Unexpected token: " + key);
            }
            if (i + 1 >= args.length) {
                throw new IllegalArgumentException("Missing value for: " + key);
            }
            String value = args[i + 1];
            map.put(key, value);
            i++;
        }
        return map;
    }

    private static Path getPath(Map<String, String> map, String key) {
        String value = map.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing required arg: " + key);
        }
        return Path.of(value);
    }

    private static void validateDirectories(Path input, Path output, Path archive) {
        if (!Files.exists(input) || !Files.isDirectory(input)) {
            throw new IllegalArgumentException("Input directory does not exist: " + input);
        }

        try {
            Files.createDirectories(output);
            Files.createDirectories(archive);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create output/archive directories");
        }
    }
}