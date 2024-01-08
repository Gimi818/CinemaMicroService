# Cinema
## A cinema management application with a ticketing system

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

I utilized a MySQL database to establish relationships within the data.
The application is deployed on Docker and incorporates Swagger for documentation.


 ## Application is developed using following technologies:
 Core:
<p align="left"><a href="https://www.java.com" target="_blank" rel="noreferrer"> 
<img src="https://ultimateqa.com/wp-content/uploads/2020/12/Java-logo-icon-1.png" alt="java" width="80" height="50"/> 
</a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://e4developer.com/wp-content/uploads/2018/01/spring-boot.png" alt="spring" width="90" height="50"/> 
<a href="https://www.mongodb.com/" target="_blank" rel="noreferrer"> <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="50" height="50"/>
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
  
  <img src="https://github.com/Gimi818/CinemaMicroService/blob/master/microservice-architecture/architecture.PNG" width="700" heigt="700"/>
