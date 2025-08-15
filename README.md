# Chat Application

A real-time chat application built with Java, JavaFX, and MySQL that supports user authentication, private/group chat, and message history.

## Features

- **User Authentication**: Secure login and registration system
- **Real-time Messaging**: Instant message delivery using Java Sockets
- **Private & Group Chats**: Support for both one-on-one and group conversations
- **Message History**: Persistent storage of all messages in MySQL database
- **Modern UI**: Clean and responsive JavaFX interface
- **Online Status**: Real-time user online/offline indicators
- **Cross-platform**: Runs on Windows, macOS, and Linux

## Technology Stack

- **Backend**: Java 11+, Java Sockets for real-time communication
- **Frontend**: JavaFX for modern desktop UI
- **Database**: MySQL for data persistence
- **Build Tool**: Maven for dependency management
- **Logging**: SLF4J with Logback
- **JSON**: Jackson for data serialization

## Prerequisites

- Java 11 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Installation & Setup

### 1. Database Setup

1. Install MySQL if you haven't already
2. Create a MySQL user or use the root user
3. Update the database configuration in `database.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/chatapp?useSSL=false&serverTimezone=UTC
db.username=your_username
db.password=your_password
```

### 2. Build the Application

```bash
# Clone the repository
git clone <repository-url>
cd chat-app

# Build with Maven
mvn clean compile
```

### 3. Run the Application

#### Option 1: Run Server First (Recommended for testing)

1. **Start the Chat Server**:
```bash
mvn exec:java -Dexec.mainClass="com.chatapp.server.ChatServer"
```

2. **Start the Client Application**:
```bash
mvn javafx:run
```

#### Option 2: Run Everything Together

```bash
mvn clean javafx:run
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── chatapp/
│   │           ├── Main.java                 # Application entry point
│   │           ├── controller/               # JavaFX controllers
│   │           │   ├── LoginController.java
│   │           │   ├── RegisterController.java
│   │           │   └── ChatController.java
│   │           ├── model/                    # Data models
│   │           │   ├── User.java
│   │           │   ├── Message.java
│   │           │   └── Chat.java
│   │           ├── server/                   # Server-side components
│   │           │   ├── ChatServer.java
│   │           │   └── ClientHandler.java
│   │           ├── client/                   # Client-side components
│   │           │   └── ChatClient.java
│   │           └── database/                 # Database management
│   │               └── DatabaseManager.java
│   ├── resources/
│   │   ├── fxml/                            # JavaFX FXML files
│   │   │   ├── LoginView.fxml
│   │   │   ├── RegisterView.fxml
│   │   │   └── ChatView.fxml
│   │   ├── css/                             # Stylesheets
│   │   │   └── styles.css
│   │   └── logback.xml                      # Logging configuration
│   └── pom.xml                              # Maven configuration
```

## Usage

### Starting the Application

1. **Login/Register**: Use the login screen to authenticate or create a new account
2. **Connect to Server**: The client automatically connects to the chat server
3. **Start Chatting**: Select a chat from the sidebar or create a new one
4. **Send Messages**: Type your message and press Enter or click Send

### Creating New Chats

- Click the "New Chat" button in the sidebar
- Enter a name for the chat
- The chat will be created and added to your chat list

### Features

- **Real-time Messaging**: Messages are delivered instantly to all participants
- **Message History**: All messages are stored and can be viewed when returning to a chat
- **User Status**: See who's online and who's offline
- **Responsive Design**: Clean, modern interface that works on different screen sizes

## Configuration

### Database Configuration

Edit `database.properties` to configure your MySQL connection:

```properties
db.url=jdbc:mysql://localhost:3306/chatapp?useSSL=false&serverTimezone=UTC
db.username=your_username
db.password=your_password
```

### Server Configuration

The chat server runs on port 8080 by default. To change this, modify the `PORT` constant in `ChatServer.java`.

### Logging Configuration

Edit `src/main/resources/logback.xml` to customize logging levels and output.

## Development

### Adding New Features

1. **New Message Types**: Extend the `Message.MessageType` enum
2. **Additional UI Views**: Create new FXML files and controllers
3. **Database Schema**: Modify `DatabaseManager.java` to add new tables
4. **Server Features**: Extend `ChatServer.java` and `ClientHandler.java`

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Handle exceptions appropriately
- Use logging for debugging and monitoring

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify MySQL is running
   - Check database credentials in `database.properties`
   - Ensure MySQL user has proper permissions

2. **Client Cannot Connect to Server**
   - Verify the chat server is running
   - Check if port 8080 is available
   - Ensure firewall allows the connection

3. **UI Not Loading**
   - Check JavaFX installation
   - Verify FXML files are in the correct location
   - Check console for error messages

### Logs

Application logs are written to:
- Console output
- `logs/chatapp.log` file

Check these logs for detailed error information.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the logs for error details

## Future Enhancements

- File sharing capabilities
- Voice and video calls
- End-to-end encryption
- Mobile application
- Web interface
- Push notifications
- Message search functionality
- User profiles and avatars
