# EduHub
**THE** platform for schools featuring Lessons scheduling, students' attendance tracking, documents sharing, and quizzes creation.\
This repo refers to the backend side of the EduHub project, you can find documentation about the Frontend side on [this link](https://github.com/mfacecchia/eduhub-frontend).\
**Note:** this project is still under development and some functionalities may not work properly or not work at all. Additional updates on both the frontend and the backend side are coming in the following weeks. If you want to test all available functionalities, a [Postman workspace](https://www.postman.com/fe-is/workspace/eduhub/collection/34159814-65d022b3-7ab5-4a7b-b9d8-6132bd3e3fe1?action=share&creator=34159814) is publicly available. All endpoints' documentation with usage instructions has been provided in the Overview page.\
For an in-depth documentation about all the classes and methods in this project, a [docs](https://github.com/mfacecchia/eduhub-backend/tree/main/docs) directory has been provided with the full source code's Javadoc.

## Table of contents
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Built In - Technologies](#built-in---technologies)
- [Libraries References](#libraries-references)
- [Environmental Variables](#environmental-variables)
- [Features](#features)

## Requirements
- Java 21
- Maven 3.9.9
- PostgreSQL
- Redis

## Quick Start
A `.env.sample` file is provided in the source code. To acknowledge all the environmental variables used in this project and how you can tweak them, you can refer to [this section](#environmental-variables) of the README.
After you completed the environmental variables configuration, you just need to run your PostgreSQL server and, in your terminal, execute the following:
```zsh
cd eduhub-backend
chmod +x scripts/*
./scripts/configure_database.sh
```
This will setup the database with all tables and load some sample data to test the application. Feel free to tweak the configuration script to fit your specific needs!
Once you completed the full app configuration, all you will have to do is runing the application. To do so, all you'll have to do is starting up your PostgreSQL database, your Redis server and then the following command
```zsh
./scripts/startup/build_server.sh
```
**Be sure to run this command while on the project's root directory.**

## Built In - Technologies
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)\
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white)

## Libraries References
- [Javalin](https://javalin.io)
- [Jedis](https://redis.io/docs/latest/develop/clients/jedis/)
- [C3P0](https://javadoc.io/doc/c3p0/c3p0/latest/index.html)
- [Auth0 JWT](https://github.com/auth0/java-jwt?tab=readme-ov-file#documentation)
- [Angus Mail](https://javadoc.io/doc/org.eclipse.angus/jakarta.mail/latest/jakarta.mail/module-summary.html)
- [PostgreSQL JDBC Driver](https://github.com/pgjdbc/pgjdbc)
- [Dotenv](https://github.com/cdimascio/dotenv-java)

## Environmental Variables
A `.env.sample` file is provided in the project with all instructions on how to use it in your project and customize it based on your needs. Here is a quick explanation:
- `DB_CONNECTION_STRING` and `REDIS_CONNECTION_STRING`, which, as the same name suggests, are used by ORMs and other libraries as information about how to connect to each persistence.
- `DB_USERNAME` and `DB_PASSWORD`, representing PostgreSQL connection username and password
- `DB_DRIVER`, the driver which will be used to interact with the Database
- `JWT_SECRET`, representing the secret which will be used to validate the received authentication token so it's important for this value to be exactly the same as the server's.
- `SMTP_HOST`, the SMTP hostname which will be used to send notifications to users (such as `smtp.gmail.com`)
- `SMTP_PORT`, the SMTP port (587 recommended but feel free to change it  based on your provider's recomendations)
- `USER_EMAIL` and `USER_PASSWORD`, the Email and password to authenticate with the SMTP provider
**Note:** once you are satisfied with the variables configuration, in order for the program to read those values you'll need to update the file name from `.env.sample` to just `.env`.

## Features
- Classes creation
- Lessons scheduling
- Notices sending
- Attendance monitoring
