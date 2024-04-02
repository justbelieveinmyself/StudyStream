# Study Stream Java Microservices
TODO: logo
![Logo](logo)

## About

Study Stream Java Microservices is a project aimed at providing an educational platform where users can access courses,
enroll in them, and access course materials. The project focuses on microservices architecture using Spring framework.

## Functional services TODO: description

#### User Service

| Method	 | Path	                  | Description	                     | User authenticated	 |
|---------|------------------------|----------------------------------|:-------------------:|
| GET	    | /api/v1/user/{userId}	 | Get specified user data	         |          ×          |
| GET	    | /api/v1/user	          | Get current user data	           |          ×          |
| PUT	    | /api/v1/user/{userId}  | Update specified user data	      |          ×          |
| PUT	    | /api/v1/user	          | Update current user data	        |          ×          |
| PATCH	  | /api/v1/user	          | Partial update current user data |          ×          |
| DELETE	 | /api/v1/user	          | Delete current user data         |          ×          |

#### Auth Service

| Method	 | Path	                  | Description	              | User authenticated	 |
|---------|------------------------|---------------------------|:-------------------:|
| POST	   | /api/v1/auth/register	 | Register new user	        |                     |
| POST	   | /api/v1/auth/refresh	  | Refresh tokens            |                     |
| POST	   | /api/v1/auth/login     | Sign In user account	     |                     |
| GET	    | /api/v1/email	         | Verify user email	        |                     |
| PATCH	  | /api/v1/email	         | Update current user email |          ×          |

#### Course Service

| Method	 | Path	                                        | Description	                                             | User authenticated	 |
|---------|----------------------------------------------|----------------------------------------------------------|:-------------------:|
| POST	   | /api/v1/course	                              | Create new course	                                       |          ×          |
| GET	    | /api/v1/course/{courseId}	                   | Get specified course data	                               |          ×          |
| PUT	    | /api/v1/course/{courseId}	                   | Update specified course data                             |          ×          |
| DELETE	 | /api/v1/course/{courseId}	                   | Delete specified course data	                            |          ×          |
| POST	   | /api/v1/course/{courseId}/module	            | Create new module in specified course	                   |          ×          |
| GET	    | /api/v1/course/{courseId}/module/{moduleId}	 | Get specified module data from specified course	         |          ×          |
| PUT	    | /api/v1/course/{courseId}/module/{moduleId}	 | Update specified module data from specified course       |          ×          |
| DELETE	 | /api/v1/course/{courseId}/module/{moduleId}	 | Delete specified module data form specified course data	 |          ×          |

## Other services TODO!

#### Mail Service

#### Api Gateway

#### Config Server

#### Eureka Server (Discovery server)

### Built With

- [Spring Boot](https://spring.io/projects/spring-boot/)
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Java](https://www.java.com/en/)

## Getting Started

### Prerequisites

- Install [Java](https://www.java.com/en/)
- Make sure you have [Maven](https://maven.apache.org) installed

### Installation TODO

#### It's not ready yet! Dockerfiles aren't created

1. Clone the repo

```shell
git clone https://github.com/justbelieveinmyself/StudyStream.git
```

2. Build the project using Maven

```shell
mvn clean install
```

## Usage

Once the project is set up and running, you can access the Swagger UI for API documentation.
Visit http://localhost:8082/swagger-ui/index.html to explore the API endpoints using OpenAPI documentation.

## Contact

Vadim - [@justbelieveinmyself](https://t.me/justbelieveinmyself)
Project Link: [https://github.com/justbelieveinmyself/StudyStream](https://github.com/justbelieveinmyself/StudyStream)

