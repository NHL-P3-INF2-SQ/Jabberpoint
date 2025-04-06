#!/bin/bash

# Exit on any error
set -e

# Create build directory if it doesn't exist
mkdir -p build

# Download JUnit Jupiter and dependencies if not present
JUNIT_PLATFORM_JAR="build/junit-platform-console-standalone-1.9.3.jar"

if [ ! -f "$JUNIT_PLATFORM_JAR" ]; then
    echo "Downloading JUnit Platform..."
    curl -L "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar" -o "$JUNIT_PLATFORM_JAR"
fi

# Set the source and test root directories
SRC_ROOT="src"
TEST_ROOT="test"

# Compile main source files
echo "Compiling source files..."
javac -d build -sourcepath $SRC_ROOT $(find $SRC_ROOT/jabberpoint -name "*.java")

# Compile test files
echo "Compiling test files..."
javac -d build -cp "$JUNIT_PLATFORM_JAR:build" -sourcepath "$TEST_ROOT:$SRC_ROOT" $(find $TEST_ROOT/jabberpoint -name "*.java")

# Copy any test resources if needed
echo "Copying test resources..."
for ext in xml jpg gif; do
    find . -maxdepth 1 -name "*.$ext" -exec cp {} build/ \; 2>/dev/null || true
    find $TEST_ROOT -name "*.$ext" -exec cp {} build/ \; 2>/dev/null || true
done

# Run tests
echo "Running tests..."
java -Dtest.environment=true -jar "$JUNIT_PLATFORM_JAR" --class-path build --scan-class-path

echo "Test run completed."