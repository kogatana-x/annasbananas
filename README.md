# Anna's Bananas eCommerce Site
Anna Andler | kogatana-x

## Project Description
*"A simplified yet comprehensive shopping system. This system will allow customers to browse products, add items to their shopping cart, place orders, and even simulate the payment process."*


## Running the Server
### Download the Server Backend 
**Option 1:** Use the Executable: 
[ExecutableDownload](https://github.com/kogatana-x/SE450/blob/Final/Final%20Project/BananaServer.exe)

**Option 2:** Use the .jar file:
[JarDownload](https://github.com/kogatana-x/SE450/blob/Final/Final%20Project/BananaServer.jar")

**Option 3:** Compile from source:
Download: [JarDownload](https://github.com/kogatana-x/SE450/blob/Final/Final%20Project/Source%20Files/)

Compile:
`javac BananaServer.java`

Execute:
`java BananaServer`

### IMPORTANT
Ensure that the backend program is located in the same directory as the html/, payment-html/ and database/ folders.

## Accessing the Site
From a browser such as Firefox, navigate to:
> http://localhost:8080

Note: I only tested the site from Firefox, so there may be issues with other browser clients. There is an occasional cookie bug when using Microsoft Edge

## Instructions for Use
The site can be used without any prior knowledge of how it works. Input and button clicks are sanitized and the users are notified if they did something wrong.

Note: the cart functionality is not available until the user has logged in.

## Known Issues
If your local network uses/supports IPv6 and IPv4, the payment server may encounter a redirect issue to and from the server - browser will throw a network protocol error. (Navigate to http://localhost:8081/payments.html, submit the form and navigate back to http://localhost:8080/finished-registration.html) to complete the process. 

Once checked out, the cart state is not wiped. 


## Design Requirements
### SOLID Principles**
 - Single Responsibility Principle (SRP):
 - Open/Closed Principle (OCP):
 - Liskov Substitution Principle (LSP):
 - Interface Segregation Principle (ISP):
 - Dependency Inversion Principle (DIP):


### Design Patterns**
 **Singleton Design Pattern:**
 - Shopping Cart
 - Logger
 - Product Catalog
 **Factory Design Pattern:**
 - ProductFactory
 **Builder Design Pattern:**
 - CartBuilder


## Implementation
This project simulates an ecommerce site called "Anna's Bananas" and a seperate simulated "External Payment Vendor" Server that processes payments. 

I chose to do a web-based implementation with asyncronous multi-threading to process client requests. The front end is uses HTML, CSS, and JavaScript (as needed). The back end is written entirely in Java. 

Both sites are hosted locally, but on seperate ports. The ecommerce site is accessible on **TCP port 8080**. The payment server is accessible on **TCP port 8081**; however, the ecommerce site will auto redirect to/from the payment site as needed. 

Note: There is no need to start the payment server, as the ecommerce site auto starts the server thread upon program build. 

## UML Diagram
The UML Diagrams and Database Architecture for this project are accessible in the below Excel sheet:
[UML XLSX Download](https://github.com/kogatana-x/SE450/blob/Final/Andler_SE450_Final_UML.xlsx)



## Link to Video Presentation
[Link](https://youtu.be/fJ5fa7j7g4Y)

## Acknowledgements.
Anna's Bananas is a fake company I made up a few years ago to build training exercises for my cybersecurity team.  I designed the logo myself; however, all other product images used in the site belong to their rightful owners. This program was made as a final project for Object Oriented Programming (SE450)


## JUnit Tests
JUnit tests have been built out for critical program functions for the Database, PaymentRepository, ProductRepository, UserRepository, UserAuthenticator, and HTML Parser classes. These tests use 'fake' databases that need to be deleted before/after each test. 


![junit](README/JUnitTests.png)





