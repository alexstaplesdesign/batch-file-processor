# Batch File Processor

Java CLI tool for batch-processing customer CSV files. Validates records,
transforms the clean ones, writes separate output and error files, then
archives the originals.

## Stack

Java 17 · Maven · SLF4J/Logback · JUnit 5 · GitHub Actions

## Quick start

```bash
git clone https://github.com/alexstaplesdesign/batch-file-processor.git
cd batch-file-processor
mvn clean package
java -jar target/batch-file-processor.jar \
  --input ./data/in \
  --output ./data/out \
  --archive ./data/archive
```

## Input format

CSV files with these columns (header row required):

| Column | Expected |
|--------|----------|
| `customer_id` | Positive integer |
| `full_name` | 2–80 characters |
| `email` | Must contain `@` and not end with it |
| `signup_date` | ISO format — `YYYY-MM-DD` |

## What it produces

For each input file `customers.csv`:

- `customers.csv.processed.csv` — clean, transformed records with a
  `processed_at` timestamp added
- `customers.csv.errors.csv` — rows that failed validation, with the row
  number, field name, and error reason

Transformations applied to valid rows: names are title-cased, emails are
lowercased, whitespace is trimmed.

## CLI options

| Flag | Required | Default | Description |
|------|----------|---------|-------------|
| `--input` | yes | — | Directory of CSV files to process |
| `--output` | yes | — | Output directory |
| `--archive` | yes | — | Where originals go after processing |
| `--pattern` | no | `*` | Glob filter (e.g. `customer*.csv`) |
| `--failOnError` | no | `false` | Exit code 4 if any rows fail validation |

## Project structure

```
src/
├── main/java/com/alex/batch/
│   ├── App.java
│   ├── cli/          # Arg parsing (Args record, ArgsParser)
│   ├── config/       # AppConfig, ConfigLoader
│   ├── io/           # CsvReader, CsvWriter, FileDiscoverer, ArchiveService
│   ├── model/        # CustomerRecord, ProcessedCustomerRecord, ValidationError
│   ├── processing/   # BatchProcessor, FileProcessor, RecordValidator, RecordTransformer
│   └── util/         # ClockProvider, Metrics
└── test/             # 22 tests across 5 test classes
```

## Tests

```bash
mvn test
```

22 tests covering the CSV reader, writer, record validator, transformer,
and full file processing flow.
