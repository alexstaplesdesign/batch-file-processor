package com.alex.batch;

import com.alex.batch.cli.Args;
import com.alex.batch.cli.ArgsParser;
import com.alex.batch.config.AppConfig;
import com.alex.batch.config.ConfigLoader;
import com.alex.batch.processing.BatchProcessor;
import com.alex.batch.processing.RecordTransformer;
import com.alex.batch.processing.RecordValidator;
import com.alex.batch.util.ClockProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        int exitCode;
        try {
            Args parsedArgs = ArgsParser.parse(args);
            AppConfig config = ConfigLoader.load(parsedArgs);

            BatchProcessor processor = new BatchProcessor(
                    config,
                    new RecordValidator(),
                    new RecordTransformer(new ClockProvider())
            );

            exitCode = processor.run();
        } catch (IllegalArgumentException e) {
            log.error("Invalid arguments/config: {}", e.getMessage());
            exitCode = 2;
        } catch (Exception e) {
            log.error("Unexpected failure", e);
            exitCode = 3;
        }

        System.exit(exitCode);
    }
}