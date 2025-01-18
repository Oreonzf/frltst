FROM flyway/flyway

COPY ./src/main/resources/db/migration /flyway/sql

CMD ["flyway", "-url=jdbc:mysql://mariadb:3306/studentdb", "-user=studentuser", "-password=studentpass", "migrate"]
