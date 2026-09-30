# Library Management System — Render Ready

Java Full Stack Library Management System using Java 17, Maven, JSP, Jakarta Servlets, MVC, JDBC, MySQL, Tomcat 10.1 and Docker.

## Architecture

Browser → JSP → Servlet → Service → DAO → JDBC → MySQL

## Deploy on Render

This is a Tomcat/JSP WAR application. Render supports Docker deployments, so this project includes a `Dockerfile` that builds the WAR with Maven and runs it on Tomcat.

1. Push the complete project to GitHub.
2. In Render, choose **New → Web Service**.
3. Connect the GitHub repository.
4. Select **Docker** as the runtime.
5. Keep the Dockerfile path as `./Dockerfile`.
6. Deploy.

## Database

The application uses MySQL. Run `database/schema.sql` on your MySQL provider before using the application.

Render's managed database service is not MySQL, so use an external MySQL provider for this MySQL version of the application.

## Render environment variables

In Render → your service → Environment, create:

`DB_URL`

Example:
`jdbc:mysql://YOUR_MYSQL_HOST:3306/library_db?useSSL=false&serverTimezone=UTC`

`DB_USER`

Your MySQL username.

`DB_PASSWORD`

Your MySQL password.

Do not put your real password in GitHub.

## Demo login

Username: `admin`

Password: `admin123`

## Local database

If the environment variables are not set, local development uses:

`jdbc:mysql://localhost:3306/library_db`

User: `root`

Password: empty by default.

## Features

- Admin login/logout
- Book management
- Member management
- Issue book
- Return book
- Fine calculation at ₹5/day
- JDBC transactions
- JSP pages
- MVC architecture
- MySQL database
- Docker/Tomcat deployment

## Security note

The demo login stores the password as plain text only for learning. For production, use BCrypt or Argon2 password hashing and proper authorization/session filters.
