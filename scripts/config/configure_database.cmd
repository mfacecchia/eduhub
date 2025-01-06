: -----------------------------------------------------------------------------------------------------
: | This script configures the database by creating the schema and all relative tables.               |
: | It also fills it out with some sample data provided in the db/dml.sql file                        |
: | IMPORTANT: this file requires execution privileges, to do so just run the following command:      |
: | `chmod +x path/to/configure_database.sh`                                                          |
: | Be sure to run this file from the `backend` directory, running it from a different directory      |
: | will cause unintended behavior or may not work at all.                                            |
: -----------------------------------------------------------------------------------------------------


@echo off
echo -----------App Configuration---------
set /p DB_USER=Enter database username: 
set /p DB_PASSWORD=Enter database password: 
echo -----------End configuration---------

echo.
echo --------Database configuration--------
et PGPASSWORD=%DB_PASSWORD%
psql -U %DB_USER% -w -c "DROP DATABASE IF EXISTS eduhub"
psql -U %DB_USER% -w -c "CREATE DATABASE eduhub"

psql -U %DB_USER% -w -d eduhub -f .\db\ddl.sql
psql -U %DB_USER% -w -d eduhub -f .\db\dml.sql
set PGPASSWORD=
echo -----------End configuration---------