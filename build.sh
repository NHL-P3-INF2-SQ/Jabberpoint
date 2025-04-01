#!/bin/bash

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files
javac -d bin src/*.java

# Create manifest file
echo "Main-Class: JabberPoint" > manifest.txt

# Create JAR file
cd bin
jar cfm ../JabberPoint.jar ../manifest.txt *
cd ..

echo "Build complete: JabberPoint.jar created successfully"
echo "Run with: java -jar JabberPoint.jar [presentation.xml]"
