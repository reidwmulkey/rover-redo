# Backend

The backend of this project is a Spring Boot application, which stores data in a MariaDB database. In order to run, you will need to import this project into eclipse as a maven project. You also need to have a local MariaDB instance running on localhost:3306, on a schema called "rover", username:root, password:root. You will need need to run the ddl.sql file located in backend/src/main/java/resource/ to generate the tables. Once you have it running, the application will run on localhost:8001.

My database currently has no indexes due to the small sample size.

### Rebuilding profiles
Rebuilding profiles is accomplished by the a POST to localhost:8001/V1/Imports/SitterReviewsFromCSV - with the CSV file passed in as a string in the request body. I wanted to do it this way so that I wouldn't have to worry about file system differences across operating systems, or issues if I host this service.

I pass each line of the CSV file into a ReviewDto object. This will handle all the "which value maps to which column" stuff.

I make 1 pass through all of the reviews in the CSV File, grabbing all unique email addresses of sitters and owners. I am treating sitters and owners as one entity - a user. I did not want to make the separation of owner vs. sitter, because if this segmentation occurs for all the services rover offers (walker, groomer, stopping in, etc.), that'll make it a nightmare for either the developers or the users.

After making 1 pass to get all the unique emails, I create a user for each email. Then I make 1 more pass through the reviews - This time creating SitterReviews. After a SitterReview is created, a SitterReviewCreatedEvent also gets persisted. It extends the abstract class (and stores on the same table as) SitterReviewEvent. The idea there being that as reviews have CRUD operations applied to them, I can use the review events to know when tore-calculate averages and sitter ranks.

I would have liked to instead use AMQP, but I didn't want to worry about also setting up an AMQP server, like rabbitMQ. So instead of requiring the other service, I set up a polling process to look for SitterReviewCreatedEvents that have not been handled. I find the sitter for that review, and then update their average stay rating and sitter rank.

### Search Ranking Algorithm
I was initially thinking about using true event sourcing for the searching algorithm, but it was just going to take too long. That is why I went for the above process. Rather than huge time complexity of event sourcing, it'll just be 1 database read to search for a minimum average stay rating. All of the necessary math is performed through business methods on the User object so that they can be asked to recalculate themselves, and it makes it easy to test.

### Building a sitter list for the UI
This is done through the GET http://localhost:8001/V1/Sitters endpoint. It has an optional query parameter of minimumAverageStayRating, that will be used by the UI. I did not include any other pagination to reduce the dataset for now, but that is pretty much the first thing to do to expand on this project.