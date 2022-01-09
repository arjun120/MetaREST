# MetaREST

MetaREST is a generic REST API which can be used to create any form of entity with a schema. On creation this schema can be further used to create, store and retrieve information in the form of objects conforming to the schema.

### Stucture

```
.
├── LICENSE
├── README.md
├── dependency-reduced-pom.xml
├── pom.xml
└── src
    └── main 
        ├── java
        │   └── com
        │       └── arjun
        │           └── metarest
        │               ├── Authentication
        │               │   └── DAuthenticator.java
        │               ├── MetaRESTApplication.java
        │               ├── MetaRESTConfiguration.java
        │               ├── dao
        │               │   ├── EntityDao.java
        │               │   └── UserDAO.java
        │               ├── domain
        │               │   ├── Entity.java
        │               │   ├── EntityMetaData.java
        │               │   ├── User.java
        │               │   └── UserDetails.java
        │               ├── mapper
        │               │   ├── EntityMapper.java
        │               │   └── UserMapper.java
        │               ├── resource
        │               │   ├── EntityResource.java
        │               │   └── MetaRESTHealthCheckResource.java
        │               └── service
        │                   └── EntityService.java
        └── resources
            └── metarest.yml
```

### Installation
Create a database which will be integrated with the application to host your data. On creation, edit the following fields in the ***metarest.yml*** file in order to connect to the database. 

```yaml
    url:
    user: 
    password: 
```

A sample configuration for the MySQL database looks like:
```yml
    url: jdbc:mysql://localhost:3306/MetaReST
    user: root
    password: password
```

#### Table creation
 We require two tables, one to store the user credentials for authentication and another table to store the entities and data corresponding to each of these. The script to create tables in MySQL are as given below:
 
 ```sql
 CREATE TABLE USERS (
 USERNAME VARCHAR(50) PRIMARY KEY,
 PASSWORD VARCHAR(50)
);

CREATE TABLE ENTITY (
ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
NAME VARCHAR(50),
DATASCHEMA VARCHAR(100),
DATA VARCHAR(100)
);
 ```


 Once the connection is made and the tables are created, run the following to install the dependencies, generate the target files and start the server:
 
```sh
mvn clean install
 ```
 
```sh
java -jar target/metarest-1.0.0.jar  src/main/resources/metarest.yml
 ```
 
 
 ### Usage
 
 Once the server is up and running, various endpoints are created which can accept requests. 
 The first step before requesting or creating any resource would be to signup/create a new user. 
 
 A user can signup at the following endpoint by sending a **POST** request with the username and password in the body as a JSON.
```sh
http://localhost:4000/entity/signup
```
Sample body for the signup request:
```json
{
    "username": "admin",
    "password": "password"
}
```

Post signup, users can register entities and further add/delete data or objects of each entity type. 

For entity creation, send a POST request to the following endpoint with a sample payload as follows:
```sh
http://localhost:4000/entity
```
```json
{
    "name": "Customer",
    "dataSchema": 
    {
        "customerName": "String",
        "age": "int"
    }
}
```

Sample response for the entity creation request is as follows:
```json
{
    "id": 6,
    "name": "Employee",
    "dataSchema": "{id=int, designation=String}"
}
```

The customer entity is now created in the database and the corresponding id for further operations is returned. 
To list all existing entities, one can send a **GET** request to the below mentioned enpoint:
```sh
http://localhost:4000/entity
```
A sample response for such a request would look like:
```json
[
    {
        "id": 6,
        "name": "Employee",
        "dataSchema": "{id=int, designation=String}"
    },
    {
        "id": 7,
        "name": "Customer",
        "dataSchema": "{customerName=String, age=int}"
    }
]
```

Once the entities are created, one can store data conforming to the *dataSchema* mentioned for the entity. For such an operation, a PUT request has to be made to the following endpoint.
```sh
http://localhost:4000/entity/<entity_id>
```
For instance, putting data into the customer entity would involve a **PUT** request to an endpoint with the request body as shown below:
```sh
 http://localhost:4000/entity/7
```
```json
{
    "customerName": "Pam",
    "age": 30
}
```

To retrieve all the data stored corresponding to a particular entity we will need to send a **GET** request to the following endpoint:
```sh
http://localhost:4000/entity/<entity_id>
```
To retrieve customer information, we would simply make a GET request on 
```sh
 http://localhost:4000/entity/7
```
The response with the customer information would look like:
```Java
{   
customerName=Michael, age=45
}
{
customerName=Pam, age=30
}
```

Finally, entities can be deleted by sending a **DELETE** request to the same endpoint as above for a specific entity. For instance, to delete the customer entity, a DELETE request should be sent to the following endpoint.
```sh
 http://localhost:4000/entity/7
```
On successful deletion, the following reponse will be seen:
```json
{
    "status": "Success"
}
```
