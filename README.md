# BookMyShow
# Implementation
- Application is developed in Spring Boot 2.4.1 with Java 11 on IntelliJ Idea. Database used is PostgresSQL.

- There are 3 types of User - Admin, TheatreAdmin and Customer

- Admin can add Cities, Movies and Theatres.

- TheatreAdmin can add MultipleScreens, Tier within Screen, Seats and Shows.

- User can register, check Theatres within city, check shows with Movie Name within city, check Theatre, check Available Seats in Show, Book Seats.

- Seats will be locked until the payments completed.If payment fails lock will be removed from the seats.

# Assumptions

For the simplicity of system, I have made following assumptions while implementing the solution -

- Payment flow is not used.

# Setup the Application

- Create a database bookmyshow using the sql file bookmyshow.sql provided in src/main/resources.

- Open src/main/resources/application.properties and change spring.datasource.username and spring.datasource.password properties as per your PostgreSQL installation.

- Type mvn spring-boot:run from the root directory of the project to run the application.

# Future Scope

- Seat locking timeout after period of time
- Payment Flow
- Login and User Account Management
