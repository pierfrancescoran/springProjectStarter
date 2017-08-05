Run the application
-------------------
The REST micro service is build using Spring core and it requires a MongoDB to authenticate the users (the dump of the DB is in the project in the folder dump).
The configurations for the different environments such us port in use, link and credentials to the DB are in the src/main/resources/properties folder and can be mofied as needed.
The SSL certificate is self signed so when testing the application (for example with Postman) tools might warn you about possible danger if accessing the application.
In test and production mode the application requires that the MongoDB provides a valid SSL certificate.
Instance of mongo
1. Run it with dev configuration:
    * build it using Maven
        $ mvn package
    * run it
        $ java -jar -Dspring.profiles.active=dev target/project-starter-application-0.1.0.jar

2. Run the test or production configuration on Docker
    * build it using Maven
        $ mvn package
    * build the image
        $ docker build -t speakingclock .
    * run the image into Docker
        $ ocker run -it --rm --publish-all=true -p 8443:8443 speakingclock
      or run the image into a Docker in swarm mode
        $ docker service create --publish 8443:8443 --replicas 1 --with-registry-auth --name speakingclock speakingclock java -jar -Dspring.profiles.active=test /home/project-starter-application-0.1.0.jar --network sspeakingclock


Use the application
-------------------
Call the web service with HTTP tools such as Postman for Chrome or HTTP requester for FireFox (in case you are using Postman first access the web service trhough the browser and authorize the access to the page even if insecure).
1. Authentication request:
address: https://192.168.0.29:8443/authenticate
method: POST
headers: Content-Type:application/json
body: {"username":"test@testington.com","password":"London","rememberMe":true}
Authentication response:
headers: Authorization:Bearer ... (to use in all the future request to the API)