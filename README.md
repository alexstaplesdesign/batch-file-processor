# 🚀 Batch File Processor

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](https://github.com/PixelPerfectDesigns/batch-file-processor/actions)

A robust Java CLI application for **batch processing customer CSV files** with validation, transformation, and error reporting capabilities. Perfect for data migration, ETL pipelines, and data quality assurance workflows.

## 📋 Table of Contents

- [Features](#-features)
- [Quick Start](#-quick-start)
- [Installation](#-installation)
- [Usage](#-usage)
- [Data Validation Rules](#-data-validation-rules)
- [Output Examples](#-output-examples)
- [Command Line Options](#-command-line-options)
- [Exit Codes](#-exit-codes)
- [Troubleshooting](#-troubleshooting)

## ✨ Features

- 📁 **Batch Processing**: Process multiple CSV files in one operation
- ✅ **Data Validation**: Comprehensive validation with detailed error reporting
- 🔄 **Data Transformation**: Clean and standardize customer data
- 📊 **Error Reporting**: Separate files for valid data and validation errors
- 🗃️ **File Archiving**: Automatically archive processed files
- 📈 **Performance Metrics**: Processing statistics and timing information
- 🛡️ **Error Handling**: Graceful handling of malformed data and edge cases

## 🚀 Quick Start

### 1. **Prerequisites**
- Java 17 or higher
- Maven 3.9 or higher

### 2. **Clone and Build**
```bash
git clone https://github.com/PixelPerfectDesigns/batch-file-processor.git
cd batch-file-processor
mvn clean package
```

### 3. **Set Up Directories**
```bash
mkdir -p data/in data/out data/archive
```

### 4. **Create Sample Data**
```bash
# Create a test CSV file
cat > data/in/customers.csv << EOF
customer_id,full_name,email,signup_date
123,john doe,JOHN.DOE@gmail.com,2023-01-15
456,jane smith,jane.smith@example.com,2023-02-20
abc,bob johnson,invalid-email,2023-03-10
789,alice brown,alice@company.org,2023-04-05
EOF
```

### 5. **Run the Processor**
```bash
java -jar target/batch-file-processor.jar \
  --input ./data/in \
  --output ./data/out \
  --archive ./data/archive \
  --pattern "*.csv" \
  --failOnError=false
```

## 📦 Installation

### Build from Source
```bash
# Clone the repository
git clone https://github.com/PixelPerfectDesigns/batch-file-processor.git
cd batch-file-processor

# Run tests
mvn clean verify

# Build executable JAR
mvn clean package
```

The executable JAR will be created at `target/batch-file-processor.jar`

## 🔧 Usage

### Basic Command Structure
```bash
java -jar target/batch-file-processor.jar [OPTIONS]
```

### Complete Example
```bash
java -jar target/batch-file-processor.jar \
  --input ./data/input \
  --output ./data/processed \
  --archive ./data/archive \
  --pattern "customer*.csv" \
  --failOnError=true
```

### Input CSV Format
Your CSV files **must** include these header columns:

| Column | Type | Description | Example |
|--------|------|-------------|---------|
| `customer_id` | Integer | Unique customer identifier (positive) | `12345` |
| `full_name` | String | Customer's full name (2-80 chars) | `John Smith` |
| `email` | String | Valid email address | `john.smith@email.com` |
| `signup_date` | Date | Account signup date (ISO format) | `2023-01-15` |

### Sample Input File (`customers.csv`)
```csv
customer_id,full_name,email,signup_date
123,john doe,JOHN.DOE@gmail.com,2023-01-15
456,jane smith,jane.smith@example.com,2023-02-20
789,bob johnson,bob.johnson@company.org,2023-03-10
```

## ✅ Data Validation Rules

The processor validates each record against these rules:

### Customer ID
- ✅ **Required**: Must not be empty
- ✅ **Format**: Must be a valid integer
- ✅ **Range**: Must be greater than 0

### Full Name
- ✅ **Required**: Must not be empty
- ✅ **Length**: Must be between 2 and 80 characters

### Email
- ✅ **Required**: Must not be empty
- ✅ **Format**: Must contain '@' and not end with '@'
- ✅ **Structure**: Basic email format validation

### Signup Date
- ✅ **Required**: Must not be empty
- ✅ **Format**: Must be a valid date format

## 📊 Output Examples

### Processed Data (`customers.csv.processed.csv`)
Clean, validated, and transformed records:
```csv
customer_id,full_name,email,signup_date,processed_at
123,John Doe,john.doe@gmail.com,2023-01-15,2026-01-30T10:30:00
456,Jane Smith,jane.smith@example.com,2023-02-20,2026-01-30T10:30:00
789,Bob Johnson,bob.johnson@company.org,2023-03-10,2026-01-30T10:30:00
```

### Error Report (`customers.csv.errors.csv`)
Records that failed validation:
```csv
row_number,field,error,original_data
4,customer_id,Must be an integer,"abc,invalid user,bad@,2023-bad-date"
4,email,Invalid format,"abc,invalid user,bad@,2023-bad-date"
4,signup_date,Invalid date format,"abc,invalid user,bad@,2023-bad-date"
```

### Console Output
```
2026-01-30 10:30:15 INFO  Discovered 1 file(s) in ./data/in
2026-01-30 10:30:15 INFO  Processing: customers.csv
2026-01-30 10:30:15 WARN  customers.csv had 1 validation error(s)
2026-01-30 10:30:15 INFO  Run complete: files=1 totalRows=4 validRows=3 invalidRows=1 timeMs=245
```

## ⚙️ Command Line Options

| Option | Required | Description | Example |
|--------|----------|-------------|---------|
| `--input` | ✅ | Directory containing CSV files to process | `./data/in` |
| `--output` | ✅ | Directory for processed output files | `./data/out` |
| `--archive` | ✅ | Directory to archive original files | `./data/archive` |
| `--pattern` | ❌ | File pattern to match (default: `*`) | `customer*.csv` |
| `--failOnError` | ❌ | Exit with error code if validation fails (default: `false`) | `true` |

### Pattern Examples
- `*.csv` - All CSV files
- `customer*.csv` - Files starting with "customer"
- `*2023*.csv` - Files containing "2023"
- `data_*.csv` - Files starting with "data_"

## 🔢 Exit Codes

| Code | Status | Description |
|------|--------|-------------|
| `0` | ✅ Success | All files processed successfully |
| `2` | ❌ Configuration Error | Invalid arguments or configuration |
| `3` | ❌ Runtime Error | Unexpected failure during processing |
| `4` | ❌ Validation Error | Validation failures when `--failOnError=true` |

## 🛠️ Troubleshooting

### Common Issues

**🚫 "No files found"**
```bash
# Check file pattern and directory
ls ./data/in/
java -jar target/batch-file-processor.jar --input ./data/in --pattern "*.csv" ...
```

**🚫 "Invalid CSV format"**
- Ensure your CSV has the required headers: `customer_id,full_name,email,signup_date`
- Check for proper CSV formatting (commas, quotes)

**🚫 "Permission denied"**
```bash
# Ensure directories are writable
chmod 755 data/out data/archive
```

**🚫 "Java not found"**
```bash
# Verify Java installation
java -version
# Should show Java 17 or higher
```

**🚫 "Build failed"**
```bash
# Clean and rebuild
mvn clean
mvn compile
mvn package
```

### Getting Help

- 📖 Check the [documentation](https://github.com/PixelPerfectDesigns/batch-file-processor)
- 🐛 Report bugs via [GitHub Issues](https://github.com/PixelPerfectDesigns/batch-file-processor/issues)
- 💡 Request features via [GitHub Discussions](https://github.com/PixelPerfectDesigns/batch-file-processor/discussions)

---

**Built with ❤️ by [PixelPerfectDesigns](https://github.com/PixelPerfectDesigns)**