# Chat Application - Project Structure

## 📁 Complete Directory Structure

```
chat-app/
├── 📄 README.md                    # Main project documentation
├── 📄 LICENSE                      # MIT License
├── 📄 pom.xml                      # Maven configuration
├── 📄 .gitignore                   # Git ignore rules
├── 📄 PROJECT_STRUCTURE.md         # This file - detailed structure
├── 📄 database.properties          # Database configuration
├── 📄 run-server.bat              # Windows batch file to start server
├── 📄 run-client.bat              # Windows batch file to start client
├── 📄 run-with-java.bat           # Alternative Java-only runner
│
├── 📁 src/
│   └── 📁 main/
│       ├── 📁 java/
│       │   └── 📁 com/
│       │       └── 📁 chatapp/
│       │           ├── 📄 Main.java              # Main application entry point
│       │           ├── 📄 SimpleMain.java        # Simplified demo version
│       │           │
│       │           ├── 📁 model/                 # Data models
│       │           │   ├── 📄 User.java          # User entity
│       │           │   ├── 📄 Message.java       # Message entity
│       │           │   └── 📄 Chat.java          # Chat entity
│       │           │
│       │           ├── 📁 controller/            # JavaFX controllers
│       │           │   ├── 📄 LoginController.java    # Login UI logic
│       │           │   ├── 📄 RegisterController.java # Registration UI logic
│       │           │   └── 📄 ChatController.java     # Main chat UI logic
│       │           │
│       │           ├── 📁 server/                # Server-side components
│       │           │   ├── 📄 ChatServer.java    # Main server class
│       │           │   └── 📄 ClientHandler.java # Individual client handler
│       │           │
│       │           ├── 📁 client/                # Client-side components
│       │           │   └── 📄 ChatClient.java    # Client connection manager
│       │           │
│       │           └── 📁 database/              # Database management
│       │               └── 📄 DatabaseManager.java # Database connection & setup
│       │
│       └── 📁 resources/
│           ├── 📁 fxml/                          # JavaFX UI layouts
│           │   ├── 📄 LoginView.fxml             # Login screen layout
│           │   ├── 📄 RegisterView.fxml          # Registration screen layout
│           │   └── 📄 ChatView.fxml              # Main chat interface layout
│           │
│           ├── 📁 css/                           # Stylesheets
│           │   └── 📄 styles.css                 # Application styling
│           │
│           └── 📄 logback.xml                    # Logging configuration
│
├── 📁 lib/                        # External dependencies (to be added)
└── 📁 target/                     # Compiled output (auto-generated)
```

## 🚀 Quick Start Options

### Option 1: Full Application (Recommended)
1. Install Maven and MySQL
2. Configure `database.properties`
3. Run `run-server.bat` then `run-client.bat`

### Option 2: Simple Demo
1. Ensure Java 11+ and JavaFX are installed
2. Run `SimpleMain.java` for a basic demonstration

### Option 3: Manual Java Compilation
1. Download required JAR files to `lib/` directory
2. Use `run-with-java.bat` for compilation and execution

## 📋 File Descriptions

### Core Application Files
- **Main.java**: Full application entry point with complete functionality
- **SimpleMain.java**: Simplified demo version for testing without dependencies

### Model Classes
- **User.java**: Represents user accounts with authentication data
- **Message.java**: Represents chat messages with metadata
- **Chat.java**: Represents chat sessions (private or group)

### Controllers
- **LoginController.java**: Handles user login and authentication
- **RegisterController.java**: Manages user registration process
- **ChatController.java**: Controls main chat interface and messaging

### Server Components
- **ChatServer.java**: Main server that handles client connections
- **ClientHandler.java**: Manages individual client communication

### Client Components
- **ChatClient.java**: Handles connection to chat server

### Database
- **DatabaseManager.java**: Manages MySQL connections and schema

### UI Resources
- **FXML files**: Define the user interface layout
- **CSS files**: Provide modern styling and theming
- **Logback**: Configures application logging

## 🔧 Configuration Files

- **pom.xml**: Maven project configuration with dependencies
- **database.properties**: Database connection settings
- **.gitignore**: Excludes build artifacts and sensitive files
- **Batch files**: Windows-specific execution scripts

## 📦 Dependencies Required

### Core Dependencies
- Java 11 or higher
- JavaFX 17+
- MySQL 8.0+
- Maven 3.6+

### External Libraries (via Maven)
- MySQL Connector/J
- Jackson (JSON processing)
- SLF4J + Logback (logging)

## 🌟 Features Implemented

✅ **User Authentication**: Login and registration system  
✅ **Real-time Chat**: Socket-based communication  
✅ **Private & Group Chats**: Support for both types  
✅ **Message History**: Persistent storage in MySQL  
✅ **Modern UI**: Clean JavaFX interface  
✅ **Cross-platform**: Windows, macOS, Linux support  
✅ **Error Handling**: Comprehensive logging and error management  
✅ **Responsive Design**: Adapts to different screen sizes  

## 📝 Notes for GitHub Upload

1. **All source code is properly organized** in the standard Maven structure
2. **Configuration files** are included for easy setup
3. **Documentation** is comprehensive and ready for users
4. **Batch files** provide Windows users with easy execution
5. **Git ignore rules** exclude build artifacts and sensitive data
6. **License** is included for open-source compliance

## 🎯 Next Steps After GitHub Upload

1. **Install Maven** on your system
2. **Set up MySQL** database
3. **Configure** `database.properties`
4. **Run the application** using provided scripts
5. **Customize** the application as needed
