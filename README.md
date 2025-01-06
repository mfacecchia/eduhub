# EduHub
**THE** platform for schools featuring Lessons scheduling, students' attendance tracking, documents sharing, and quizzes creation.

## Table of contents
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Built In - Technologies](#built-in---technologies)
- [Modules References](#modules-references)
- [Environmental Variables](#environmental-variables)
- [Features](#features)

## Requirements
- Java 21
- Maven 3.9.9
- Node.JS
- PostgreSQL
- Redis

## Quick Start
A `.env.sample` file is provided in the source code. To acknowledge all the environmental variables used in this project and how you can tweak them, you can refer to [this section](#environmental-variables) of the README.
After you completed the environmental variables configuration, you just need to run your PostgreSQL server and, in your terminal, execute the following:
```zsh
cd eduhub
chmod +x scripts/*
./scripts/configure_database.sh
cd frontend
npm install
```
This will setup the database with all tables and load some sample data to test the application. Feel free to tweak the configuration script to fit your specific needs!
Once you completed the full app configuration, all you will have to do is runing the application. To do so, all you'll have to do is starting up your PostgreSQL database, your Redis server and then the following command
```zsh
./scripts/startup/build_server.sh
```
for the backend, and
```zsh
cd frontend
npm run dev
```
to start the frontend server.

## Built In - Technologies

## Modules References

## Environmental Variables
A `.env.sample` file is provided in the project for both the [Frontend](https://github.com/mfacecchia/eduhub/blob/main/frontend/.env.sample) and the [Backend](https://github.com/mfacecchia/eduhub/blob/main/backend/.env.sample) with all instructions on how to use it in your project and customize it based on your needs. Here is a quick explanation:
#### Frontend
- `VITE_BACKEND_ADDRESS`
- `VITE_PAGE_TITLE`
#### Backend
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
