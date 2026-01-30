package com.alex.batch.config;

import com.alex.batch.cli.Args;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigLoader {
    private ConfigLoader() { }

    public static AppConfig load(Args args) {
        Properties props = new Properties();
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (Exception ignored) {
            // Default to CLI values if config is missing or unreadable.
        }

        String pattern = props.getProperty("app.pattern", args.pattern());
        boolean failOnError = Boolean.parseBoolean(
                props.getProperty("app.failOnError", Boolean.toString(args.failOnError()))
        );

        return new AppConfig(
                args.inputDir(),
                args.outputDir(),
                args.archiveDir(),
                pattern,
                failOnError
        );
    }
}