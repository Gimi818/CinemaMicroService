# Cinema
## A cinema management microservice with a ticketing system

The new user registers within the application and completes the email verification process by clicking the activation link received in their email.
Once verified, the user proceeds to select a screening date and choose a film for that specific time, while also checking the seat availability for the chosen screening.

Moving on to the next step, the user initiates the ticket booking process. They select a seat, specify the row, and choose a discount type. 

The application provides two discount options : 

- Events: Cheap Tuesday and Cheap Friday, offering a 20% reduction in ticket prices.

- Student discounts: An additional 10% off on ticket prices.

After a successful booking, the application sends an email containing the ticket in PDF format, 
which includes all the details along with a unique QR code for confirming the purchase and details of the ticket

The application is integrated with the National Bank of Poland's API, allowing customers to choose from 34 currencies for the ticket price. The application sends a periodic request to the API NBP every 24 hours, ensuring the database is updated with the latest currency values.

User have the authority to add new films to the database and create new screenings, specifying the date, time, and film. 

Apache Kafka:

* The User and Ticket microservices are integrated with Apache Kafka, which sends messages to the EmailSender application.
* Kafka is responsible for passing the data needed to send the confirmation email.
* When a user completes the ticket reservation process, the reservation microservice publishes a message to Kafka with details of the ticket purchased.
* The microservice responsible for generating and sending PDF tickets consumes this message, generates a ticket and then sends it to the user.
* Using Kafka in this process ensures high availability and scalability of the system, enabling it to handle a large volume of reservations.

Configuration:
* Microservices operate on other ports and API Gateway serves as an intermediary layer between various microservices.
  This allows you to use a single port or endpoint to communicate with different services within the microservices architecture.
* I utilized a Postgres database to establish relationships within the data.
* All databases are containerized in DOCKER
* Zipkin traces HTTP requests passing through various microservices in an application.
* Eureka Server registers microservices and informs about their availability.
* ConfigServer is responsible for centralized storage and distribution of configuration for microservices.
* Microservices communicate with each other over HTTP by exposing endpoints and using Feign Client.

 ## Application is developed using following technologies:
 Core:
<p align="left"><a href="https://www.java.com" target="_blank" rel="noreferrer"> 
<img src="https://ultimateqa.com/wp-content/uploads/2020/12/Java-logo-icon-1.png" alt="java" width="80" height="50"/> 
</a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://e4developer.com/wp-content/uploads/2018/01/spring-boot.png" alt="spring" width="90" height="50"/> 
<a href="https://www.mongodb.com/" target="_blank" rel="noreferrer"> <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="50" height="50"/>
  <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://blog.min.io/content/images/2021/09/1_kqpVTzo8b0e2oKdOjWQxZA.png" alt="java" width="99" height="50"/></a>
 <a href="https://git-scm.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="50" height="50"/> </a> 
 <a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://www.zdnet.com/a/img/resize/e7aff3398e12f0fa70fd66238d743054c4c8b95e/2018/04/19/092cbf81-acac-4f3a-91a1-5a26abc1721f/postgresql-logo.png?auto=webp&fit=crop&height=900&width=1200" alt="mysql" width="80" height="50"/> </a>
 <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://mapstruct.org/images/mapstruct.png" alt="docker" width="80" height="50"/></a>
 </a> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://junit.org/junit4/images/junit5-banner.png" alt="java" width="90" height="50"/>
 <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://javadoc.io/static/org.mockito/mockito-core/1.9.5/org/mockito/logo.jpg" alt="java" width="90" height="50"/></a>
 <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://static.javatpoint.com/tutorial/spring-cloud/images/spring-cloud.png" alt="java" width="70" height="50"/></a>
  <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://user-images.githubusercontent.com/27962005/35682934-68b84abe-0730-11e8-926d-66ae93aa4b1d.png" alt="java" width="90" height="55"/></a>
   <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://static.wixstatic.com/media/fb5029_919b4bcf47a346b091751ccfc87a2d08~mv2.png" alt="java" width="90" height="50"/></a>
 </p>

  ## Microservice Architecture
  
  <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/microservice-architecture/architectureMS.PNG" width="1000" heigt="700"/>
  
  ## Demonstration of the use of a microservice on YouTube:
[![Cinema](https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/ytpic.PNG)](https://youtu.be/wAsoqzuN98A)
Link : https://youtu.be/wAsoqzuN98A

   ## To run the application, follow these steps :
- Install IntelliJ IDEA and Docker Desktop on your computer.
- Run Docker Desktop.
- Clone the repository in IntelliJ IDEA using the link https://github.com/Gimi818/CinemaMicroService
- Run docker-compose up in the terminal. 
- Run the ConfigServer, DiscoveryServer, and Gateway  in IntelliJ IDEA.
- Run the other services: ticket, screening, user, film, and currencies  in IntelliJ IDEA.
- Try the applications in Postaman, the steps on how to do it are below.
 
 ## How to use the application in Postman:
 
    Step 1 :
    POST localhost:8222/api/v1/users/registration
    Enter your data.
    JSON:
    {
     "firstName":"Wojciech",
     "lastName":"Gmiterek",
     "email":"cinemaemailtest@gmail.com",
     "password":"password",
    "repeatedPassword" :"password"
    }
  
  <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/1.PNG" width="500" heigt="700"/>

    Step 2 :
    Click on the account activation link.

  <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/email.PNG" width="500" heigt="700"/>
  
    Step 3 :
    GET localhost:8222/api/v1/screenings?date=2024-04-23
    Select the desired cinema date, input it into the URL, and discover a film you'd like to watch.
    (screenings are available from 2024-06-19 to 2024-07-24)
    
   <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/2.PNG" width="600" heigt="800"/>

    Step 4 :
    GET localhost:8222/api/v1/screenings/1
    Enter the film ID into the URL to check the available seats for the screening.
    
   <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/3.PNG" width="500" heigt="700"/>

    Step 4 :
    GET localhost:8222/api/v1/codes
    Check available currencies in which you can receive the ticket price.
    
   <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/4.PNG" width="500" heigt="700"/>

    Step 5 :
    POST localhost:8222/api/v1/book/1/1
    Enter the user ID and then the film ID.
    Choose the ticket type NORMAL or REDUCE if you are a student, you are qualified for a discount.
    Select the currency in which you would like to receive the ticket price, you can choose from 34 currencies.
    Enter the row number or seat number. 
    JSON:
    {
     "ticketType":"REDUCE",
     "currency":"USD",
     "rowsNumber":5,
     "seatInRow": 8
    }
  
  <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/5.PNG" width="500" heigt="700"/>

    Step 6 :
    You have received an email with a PDF ticket containing the details along with a QR code confirming your purchase.
    Scan the QR code and check purchase details
    
   <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/ticket.PNG"  width="500" heigt="700"/>


    Add the film to the database:
    POST localhost:8222/api/v1/films
    Enter the film data.
    JSON:
    {
     "title": "BAD BOYS 3",
     "category": "ACTION",
     "durationFilmInMinutes": 130
    }
  
  <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/6.PNG" width="500" heigt="700"/>
  
    Create a screening:
    POST localhost:8222/api/v1/screenings/10
    Enter the ID in the URL of the film for which you want to create.
    Enter the date and time of screening.
    JSON:
    {
     "date": "2024-02-01",
     "time": "22:30"
     }
  
  <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/steps/steps/7.PNG" width="500" heigt="700"/>


