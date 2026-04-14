# Batch File Processor

Java CLI tool for batch-processing customer CSV files — validates records,
transforms data, reports errors, and archives the originals.

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

CSV files with columns: `customer_id`, `full_name`, `email`, `signup_date`

## What it does

- Validates each row (ID format, email structure, date format, name length)
- Normalizes data (title case names, lowercase emails)
- Writes clean records to a `.processed.csv` file
- Writes failed rows to a `.errors.csv` file with reason
- Archives the original after processing

## Options

| Flag | Required | Default | Description |
|------|----------|---------|-------------|
| `--input` | yes | — | Directory of CSV files to process |
| `--output` | yes | — | Output directory |
| `--archive` | yes | — | Archive directory for originals |
| `--pattern` | no | `*` | File glob filter |
| `--failOnError` | no | `false` | Exit non-zero if any rows fail |
