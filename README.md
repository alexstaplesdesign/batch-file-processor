# batch-file-processor

Java CLI batch processor that reads customer CSV files, validates and transforms rows, writes processed output + error reports, and archives inputs.

## Requirements
- Java 17
- Maven 3.9+

## Build and test
mvn clean verify

## Build jar
mvn clean package

## Run
java -jar target/batch-file-processor.jar \
  --input ./data/in \
  --output ./data/out \
  --archive ./data/archive \
  --pattern "*.csv" \
  --failOnError=false

## Input CSV contract
Required header columns:
- customer_id
- full_name
- email
- signup_date

## Output
For each input file:
- <filename>.processed.csv
- <filename>.errors.csv

## Exit codes
- 0 success
- 2 invalid args/config
- 3 unexpected failure
- 4 validation failures when failOnError=true

## Local run quick start
mkdir -p data/in data/out data/archive
Copy a CSV into data/in then run the jar command above.