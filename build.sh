#!/bin/bash

# Exit on any error
set -e

# Create build directory if it doesn't exist
mkdir -p build

# Set the source root directory
SRC_ROOT="src"

# Compile all Java files with proper classpath and source root
echo "Compiling Java files..."
javac -d build -sourcepath $SRC_ROOT $(find $SRC_ROOT/jabberpoint -name "*.java")

# compile to jar
echo "Compiling to jar..."
jar cfm JabberPoint.jar manifest.txt -C build .

# Copy resources if any exist
echo "Copying resources..."
for ext in xml jpg gif; do
    find . -maxdepth 1 -name "*.$ext" -exec cp {} build/ \; 2>/dev/null || true
    find $SRC_ROOT -name "*.$ext" -exec cp {} build/ \; 2>/dev/null || true
done

echo "Build completed successfully. Output is in the build directory."
