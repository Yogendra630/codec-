# 🚀 Chat Application Setup Guide

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 11 or higher** (OpenJDK or Oracle JDK)
- **JavaFX 17+** (included with Java 11+)
- **MySQL 8.0+** (for database functionality)
- **Maven 3.6+** (for dependency management)

## 🔧 Installation Steps

### Step 1: Install Java
1. Download Java from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
2. Install and add to your system PATH
3. Verify installation: `java -version`

### Step 2: Install Maven
1. Download Maven from [Apache Maven](https://maven.apache.org/download.cgi)
2. Extract to a directory (e.g., `C:\Program Files\Apache\maven`)
3. Add Maven bin directory to your system PATH
4. Verify installation: `mvn -version`

### Step 3: Install MySQL
1. Download MySQL from [MySQL Downloads](https://dev.mysql.com/downloads/)
2. Install MySQL Server and MySQL Workbench
3. Create a root user or new user with appropriate permissions
4. Note down your username and password

### Step 4: Configure Database
1. Open `database.properties` in the project root
2. Update the following lines with your MySQL credentials:
   ```properties
   db.username=your_username
   db.password=your_password
   ```
3. Ensure MySQL is running on port 3306

## 🎯 Running the Application

### Option 1: Using Maven (Recommended)

#### Start the Chat Server
```bash
# In the project directory
mvn exec:java -Dexec.mainClass="com.chatapp.server.ChatServer"
```

#### Start the Chat Client
```bash
# In a new terminal, in the project directory
mvn javafx:run
```

### Option 2: Using Batch Files (Windows)

1. **Start Server**: Double-click `run-server.bat`
2. **Start Client**: Double-click `run-client.bat`

### Option 3: Manual Java Compilation

1. **Compile the project**:
   ```bash
   javac -cp "lib/*" -d target/classes src/main/java/com/chatapp/*.java src/main/java/com/chatapp/*/*.java
   ```

2. **Run the application**:
   ```bash
   java -cp "target/classes;lib/*" com.chatapp.Main
   ```

## 🧪 Testing the Application

### First Run
1. The application will start with a login screen
2. Click "Create Account" to register a new user
3. Fill in the registration form and submit
4. Return to login and use your credentials
5. You'll see the main chat interface

### Creating Chats
1. Click "New Chat" in the sidebar
2. Enter a chat name
3. The chat will appear in your chat list
4. Select a chat to start messaging

### Sending Messages
1. Select a chat from the sidebar
2. Type your message in the input area
3. Press Enter or click Send
4. Messages appear in real-time

## 🔍 Troubleshooting

### Common Issues

#### 1. "Maven not found" error
- **Solution**: Install Maven and add to PATH
- **Alternative**: Use the batch files or manual Java compilation

#### 2. "Database connection failed" error
- **Solution**: Check if MySQL is running
- **Solution**: Verify credentials in `database.properties`
- **Solution**: Ensure MySQL user has proper permissions

#### 3. "JavaFX not found" error
- **Solution**: Install Java 11+ which includes JavaFX
- **Solution**: Download JavaFX separately and add to classpath

#### 4. "Port 8080 already in use" error
- **Solution**: Change the port in `ChatServer.java`
- **Solution**: Stop other applications using port 8080

#### 5. "FXML files not found" error
- **Solution**: Ensure you're running from the project root directory
- **Solution**: Check that FXML files are in `src/main/resources/fxml/`

### Getting Help

1. **Check the logs**: Look for error messages in the console
2. **Verify file structure**: Ensure all files are in the correct locations
3. **Check Java version**: Ensure you're using Java 11 or higher
4. **Database connectivity**: Test MySQL connection separately

## 📱 Features to Test

- ✅ **User Registration**: Create new accounts
- ✅ **User Login**: Authenticate existing users
- ✅ **Chat Creation**: Make new private/group chats
- ✅ **Real-time Messaging**: Send and receive messages
- ✅ **Message History**: View previous conversations
- ✅ **Modern UI**: Navigate the clean interface

## 🎨 Customization

### Changing the Theme
- Edit `src/main/resources/css/styles.css`
- Modify colors, fonts, and layout styles

### Adding New Features
- Extend the model classes in `src/main/java/com/chatapp/model/`
- Add new controllers in `src/main/java/com/chatapp/controller/`
- Modify server logic in `src/main/java/com/chatapp/server/`

### Database Schema
- Modify `DatabaseManager.java` to add new tables
- Update model classes to match schema changes

## 🚀 Next Steps

After successful setup:

1. **Explore the codebase** to understand the architecture
2. **Test all features** to ensure everything works
3. **Customize the application** for your needs
4. **Deploy to production** if needed
5. **Contribute improvements** to the project

## 📞 Support

If you encounter issues:

1. Check this setup guide first
2. Review the `README.md` for additional information
3. Check the `PROJECT_STRUCTURE.md` for file organization
4. Look at console logs for error details
5. Ensure all prerequisites are properly installed

---

**Happy Chatting! 🎉**
