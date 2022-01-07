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

