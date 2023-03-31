<h1>Bookstore Application </h1>

   <p>BookStore Application provides the rest api's to add a book,update a book,remove a book 
   ,get all the books and search the books.
   Below are the api's developed </p>

1.Add a book

    POST /api/v1/books 
    
      Sample Request:
      
         {
             "title":"book3",
             "type":"ebook",
             "volumes":5,
             "genre":"fiction",
             "author":"priya"
         }
      
      Sample Response:

      {
          "id": "041758f2-5f83-4e67-bbf3-01f4fa9e30e5",
          "title": "book3",
          "type": "ebook",
          "volumes": 5,
          "genre": "fiction",
          "author": "priya"
      }

2.Get all books

    GET /api/v1/books
    
    Sample Response:
    [
       {
           "id": "041758f2-5f83-4e67-bbf3-01f4fa9e30e5",
           "title": "book3",
           "type": "ebook",
           "volumes": 5,
           "genre": "fiction",
           "author": "priya"
       }
    ]

3.Update a book

    PUT /api/v1/books/{id}  
    Ex:/api/v1/books/041758f2-5f83-4e67-bbf3-01f4fa9e30e5
    Sample Request: 
     {
          "title": "book5",
          "type": "ebook",
          "volumes": 5,
          "genre": "fiction",
          "author": "priya"
      }
    Sample Response:
      {
         "id": "041758f2-5f83-4e67-bbf3-01f4fa9e30e5",
         "title": "book5",
         "type": "ebook",
         "volumes": 5,
         "genre": "fiction",
         "author": "priya"
      }

4.Delete the book

    DELETE /api/v1/books/{id}  
      Ex:/api/v1/books/041758f2-5f83-4e67-bbf3-01f4fa9e30e5
      Success Response: 202

5.Search API:
The Search API works on generic rules where the front end can provide the multiple criteria's based on the
requirement.<br>
for Ex:<br>
1.AllBooks <br>
2.Books with more than 4 volumes <br>
3.Books with particular author but not specific genre.

    GET /api/v1/books/search

    Sample Request:
    [
       {
           "key":"volumes",
           "operation":">",
           "value":"1"
       },
       {
           "key":"type",
           "operation":"eq",
           "value":"ebook"
       },
       {
           "key":"author",
           "operation":"like",
           "value":"priya"
       }
    ]

    Sample Response:
    [
       {
           "id": "62d3dd7b-009c-4302-8186-8621bb1dcce4",
           "title": "book3",
           "type": "ebook",
           "volumes": 5,
           "genre": "fiction",
           "author": "priya"
       }
    ]

6. Get Book based on Id:
<pre>GET /api/v1/books/{id}  
Sample Request: /api/v1/books/62d3dd7b-009c-4302-8186-8621bb1dcce4
Sample Response:
   {
      "id": "62d3dd7b-009c-4302-8186-8621bb1dcce4",
      "title": "book3",
      "type": "ebook",
      "volumes": 5,
      "genre": "fiction",
      "author": "priya"
      }
</pre>

7. Sample Data Import:<br>
   Any Application is incomplete without the data, so there is a sample data available as part of application, which can be imported with the endpoint below.
   <pre>
   /api/v1/books/loadSampleData
   </pre>
<h3>Following Technologies Used</h3>

    1.IntelliJ IDE
    
    2.Java 11
    
    3.Springboot
    
    4.Docker

    5.MySQL

    6.Maven

    7. Git

<h3>Steps to Run the Application</h3>

<h4> Method 1: using Docker </h4>

Prerequisites:

1. Git
2. Docker with Compose

<li>Clone the repository</li>
<pre>
   git clone https://github.com/haripriya517/bookstore.git
   cd bookstore
</pre>
<li>update the Database passwords in the .env file.</li>
<pre>
   vi .env
</pre>
<li>Run Docker Compose to run the application with Build.</li>
<pre>
   docker-compose up --build
</pre>

<li>
   Open the Url in the Browser with the port mentioned in .env file, Default is 8080
</li>

<pre>
   http://localhost:8080/swagger-ui/
</pre>

<h4> Method 2: Manual Running </h4>
Prerequisites:

1. Git
2. Java 11
3. Mysql
4. Maven

1. Clone the application from git

<pre>
   git clone https://github.com/haripriya517/bookstore.git
   cd bookstore
</pre>

2. Create Mysql database
   <pre>
   create database bookstore
   </pre>

3. Change <b>mysql username and password</b> as per your installation

<pre>
    open src/main/resources/application.properties

    change spring.datasource.username and spring.datasource.password as per your mysql installation
</pre>

4. Build and run the app using locally
   <pre>
      mvn package
      java -jar target/spring-boot-rest-api-tutorial-0.0.1-SNAPSHOT.jar
   </pre>
   or <br>
   Alternatively, you can run the app without packaging it using -
      <pre>      mvn spring-boot:run </pre>

and Navigate to
<pre>
   http://localhost:8080/swagger-ui/
</pre>

