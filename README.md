# Blog Platform with Word Frequency Analysis

A Spring Boot-based blogging platform that allows users to create, manage blog posts, and analyze word frequencies in their content. The platform includes user authentication, blog management, and a unique word frequency analysis feature that helps users understand their writing patterns.

## Features

- **User Authentication**
  - User registration and login
  - Secure password handling with BCrypt encryption
  - Session-based authentication

- **Blog Management**
  - Create new blog posts
  - Edit existing posts
  - Delete posts
  - View all blog posts
  - Read individual blog posts

- **Word Frequency Analysis**
  - Analyze word frequency across all user's blog posts
  - Display top 5 most frequently used words
  - Smart handling of possessive forms (e.g., "java's" counts as "java")
  - Excludes common stop words

## Technology Stack

- **Backend**
  - Java 17
  - Spring Boot 3.1.5
  - Spring Security
  - Spring Data JPA
  - MySQL Database

- **Frontend**
  - Thymeleaf template engine
  - Bootstrap 5.1.3
  - HTML/CSS

## Prerequisites

Before you begin, ensure you have the following installed:
- Java Development Kit (JDK) 17 or higher
- MySQL Server 8.0 or higher
- Maven 3.6 or higher
- Your favorite IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Git for version control

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/blog-platform.git
cd blog-platform
```

### 2. Database Setup
1. Open MySQL Workbench or your MySQL client
2. Create a new database:
   ```sql
   CREATE DATABASE blog_db;
   ```
3. Update database configuration in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   server.port=8081
   ```

### 3. Build and Run
1. Open a terminal in the project root directory
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will start on `http://localhost:8081`

## Using the Application

### 1. Registration and Login
1. Access `http://localhost:8081` in your web browser
2. Click "Register" to create a new account
3. Fill in the registration form:
   - Username
   - Password
4. After registration, you'll be redirected to the login page
5. Log in with your credentials

### 2. Creating a Blog Post
1. After logging in, you'll see the blog list page
2. Click "Create New Blog" button
3. Fill in the blog form:
   - Title: Enter your blog post title
   - Content: Write your blog post content
4. Click "Save" to publish your post

### 3. Managing Blog Posts
- **View Posts**: All posts are visible on the blog list page
- **Edit Post**: 
  1. Click "Edit" on your own posts
  2. Modify the title or content
  3. Click "Save" to update
- **Delete Post**: 
  1. Click "Delete" on your own posts
  2. Confirm deletion when prompted

### 4. Word Frequency Analysis
1. Click "Word Frequency Report" button on the blog list page
2. View your most frequently used words across all your blog posts
3. The analysis:
   - Shows top 5 most used words
   - Excludes common words like "the", "and", etc.
   - Combines word variations (e.g., "java's" → "java")

### 5. Logging Out
- Click the "Logout" button in the top right corner
- You'll be redirected to the login page

## Common Issues and Solutions

1. **Port Already in Use**
   ```
   Error: Port 8081 is already in use
   ```
   Solution: Either free up port 8081 or change the port in application.properties

2. **Database Connection Failed**
   ```
   Error: Unable to connect to database
   ```
   Solutions:
   - Verify MySQL is running
   - Check database credentials in application.properties
   - Ensure database exists

3. **Java Version Mismatch**
   ```
   Error: Java version not compatible
   ```
   Solution: Ensure you're using Java 17 or higher:
   ```bash
   java -version
   ```

## Development and Testing

### Running Tests
```bash
mvn test
```

### Development Mode
1. Enable debug logging in `application.properties`:
   ```properties
   logging.level.com.blog=DEBUG
   ```
2. Run with development profile:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

## Contributing

1. Fork the repository
2. Create your feature branch:
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. Push to the branch:
   ```bash
   git push origin feature/AmazingFeature
   ```
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Spring Boot framework
- Bootstrap for UI components
- All contributors who have helped with the project
