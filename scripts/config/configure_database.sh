# -----------------------------------------------------------------------------------------------------
# | This script configures the database by creating the schema and all relative tables.               |
# | It also fills it out with some sample data provided in the db/dml.sql file                        |
# | IMPORTANT: this file requires execution privileges, to do so just run the following command:      |
# | `chmod +x path/to/configure_database.sh`                                                          |
# | Be sure to run this file from the `backend` directory, running it from a different directory      |
# | will cause unintended behavior or may not work at all.                                            |
# -----------------------------------------------------------------------------------------------------


# Database connection credentials (input is silent
# so the keys will not be printed out in the terminal)
echo "-----------App Configuration---------"
read -p "Enter database username: " DB_USER
read -sp "Enter database password: " DB_PASSWORD
echo
echo "-----------End configuration---------\n\n"


# Database configuration, be sure that the postgres database is open
echo "--------Database configuration--------"
PGPASSWORD=$DB_PASSWORD psql -U $DB_USER -c "DROP DATABASE IF EXISTS eduhub"
PGPASSWORD=$DB_PASSWORD psql -U $DB_USER -c "CREATE DATABASE eduhub"

# Tables creation and sample data fillout
PGPASSWORD=$DB_PASSWORD psql -U $DB_USER -d eduhub -f ./db/ddl.sql
PGPASSWORD=$DB_PASSWORD psql -U $DB_USER -d eduhub -f ./db/dml.sql
echo "-----------End configuration---------\n\n"