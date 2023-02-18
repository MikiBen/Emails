# Onwelo Mails Application

### Application Properties
Software: IntelliJ IDEA 2022.2.3 (Community Edition)

Language: Java 11

Build system and framework: Maven 4.0.0

JUnit 5.9.2

h2database 1.4.200

Spring Boot 3.0.2


JDK: Oracle OpenJDK 11.0.17

### How to get All Users ?
curl --location 'http://localhost:8080/users/getAll'

### How to create or update an user ?
curl --location 'http://localhost:8080/users/create' \
--header 'Content-Type: application/json' \
--data-raw '{
"id" : 1,
"email" : "test@gmail.com",
"name" : "Test test"
}'

### How delete an user with id = 1
curl --location --request DELETE 'http://localhost:8080/delete?id=1

### How get user with id = 3
curl --location 'http://localhost:8080/users/get?id=3'

### How send emails to all users
curl --location 'http://localhost:8080/mail/sendToAll' \
--header 'Content-Type: application/json' \
--data '{
"subject" : "TEST",
"message" : "Test message"
}'
### Logs
Aplicatiuon create to files with logs:

access.log - logs with request

emails.log - general logs

### Author
Miki