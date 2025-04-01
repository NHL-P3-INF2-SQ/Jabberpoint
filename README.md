# Rework

## Students

- Bram Suurd (5371333)
- Jesse van der Voet (5405416)

# JabberPoint Presentation Tool

JabberPoint is a Java-based presentation tool that allows you to create and display simple presentations. It supports text slides with different styles and levels, as well as image slides.

## Features

- Text slides with multiple style levels
- Image support
- XML-based presentation file format
- Built-in demo presentation
- Keyboard navigation
- Menu-based controls

## Requirements

- Java 21 or higher

## Running JabberPoint

1. Download the latest `JabberPoint.jar` from the [Releases](../../releases) page
2. Run the application using:
   ```bash
   java -jar JabberPoint.jar [presentation.xml]
   ```
   - If no file is specified, it will show the demo presentation
   - You can specify an XML file to load a custom presentation

## Navigation

- **Next slide**: Page Down, Enter, or Down Arrow
- **Previous slide**: Page Up or Up Arrow
- **Exit**: Q key or File -> Exit
- **Open presentation**: File -> Open
- **New presentation**: File -> New
- **Save presentation**: File -> Save

## Creating Presentations

Presentations are stored in XML format. Here's an example structure:

```xml
<?xml version="1.0"?>
<!DOCTYPE presentation SYSTEM "jabberpoint.dtd">
<presentation>
  <showtitle>Demo Presentation</showtitle>
  <slide>
    <title>Title Slide</title>
    <item kind="text" level="1">Main point</item>
    <item kind="text" level="2">Sub point</item>
    <item kind="image" level="1">image.jpg</item>
  </slide>
</presentation>
```

## Building from Source

1. Clone the repository
2. Ensure you have JDK 21 installed
3. Make the build script executable and run it:
   ```bash
   chmod +x build.sh
   ./build.sh
   ```
   This will:
   - Create a `build` directory
   - Compile all Java source files
   - Copy any resources (XML, JPG, GIF files) to the build directory

The compiled output will be available in the `build` directory.

## License

This program is distributed under the terms of the accompanying COPYRIGHT.txt file.
