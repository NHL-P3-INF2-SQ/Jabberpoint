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

## Design Patterns

The rework of JabberPoint implements several design patterns to improve code organization, maintainability, and flexibility:

### Observer Pattern

- Used for presentation state changes notification
- Implemented through `PresentationSubject` and `PresentationObserver` interfaces
- Allows UI components to update automatically when presentation state changes
- Found in `SlideViewerComponent` and other UI elements

### Command Pattern

- Implements all user actions as command objects
- Each command (Next, Previous, Open, Save, etc.) encapsulates a specific action
- Commands are executed through a common `Command` interface
- Provides easy extension for new commands and better separation of concerns

### Factory Pattern

- `StyleFactory` creates different style implementations for text formatting
- `SlideItemFactory` handles creation of different types of slide items (text and images)
- Centralizes object creation logic and improves maintainability

### Bridge Pattern

- Used in `SlideViewerComponent` for rendering
- Separates abstraction (`SlideViewerComponent`) from implementation (`PresentationRenderer`)
- Allows for different rendering implementations without changing the viewer component

### SOLID Principles Application

The project demonstrates the application of SOLID principles, specifically focusing on the XMLAccessor class:

#### Single Responsibility Principle (SRP)

- The XMLAccessor class has been refactored to have a single responsibility: handling XML file operations
- It focuses solely on reading from and writing to XML files
- Other responsibilities like presentation management and data transformation have been separated into their own classes

#### Open/Closed Principle (OCP)

- The XMLAccessor is designed to be open for extension but closed for modification
- New XML handling features can be added through extension rather than modifying existing code

#### Interface Segregation Principle (ISP)

- The XMLAccessor implements focused interfaces that are specific to its XML handling responsibilities
- Clients are not forced to depend on methods they don't use

#### Dependency Inversion Principle (DIP)

- The XMLAccessor depends on abstractions rather than concrete implementations
- High-level modules that use XMLAccessor depend on its interface rather than the concrete class

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
