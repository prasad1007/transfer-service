# TransferServiceAPI
This service application holds API to transfer money between accounts.

### Prerequisite
- Maven
- JDK 1.8+
### Project Structure
```bash
TransferServiceAPI
├── src
│   ├── main
│   │   ├── java\com\example\transferservice
│   │   └── resources
│   └── test
│       ├── java\com\example\transferservice
│       └── resources
├── .gitignore
├── pom.xml
└── README.md
```
### Packaging
```
mvn package
```
### Test
```
mvn test
```
### Running
```
java -jar transfer-service-0.0.1-SNAPSHOT.jar
```
### Data
Initial data (src\main\resources\data.sql) will be loaded in the H2 database when application start.
> INSERT INTO ACCOUNTS (ACCOUNTNUMBER, BALANCE) VALUES
> (34000001, 100),
> (34000002, 100);
## Feature
This application is for demo only. It provides APIs for following single feature
- Create Transaction
### Basic API Information
| Method | Path | Usage |
| --- | --- | --- |
| POST | /v1/transaction/transfer | transfer amount between accounts |
### H2 Database
H2 database is available at URL - http://localhost:8080/h2/
### Library used
| Library | Usage |
| --- | --- |
| spring boot | To create spring boot application |
| spring data jpa | To create Database operations |
| H2 | To create in-memory database |
| Spotless | Code Formatting |
