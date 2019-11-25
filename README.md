# numberstowords

This is a simple web application which allows a user to convert a number to English word equivalent.

Following are the requirements for running the application
1. Java development kit 1.8 and above
2. Maven 3.3.9 and above
3. Git - Latest version
4. Tortoise Git (Optional) - Latest Version

Assumption - All the software’s mentioned in requirements are installed. JAVA_HOME environment variable is set and is pointing to Java development kit installation location. MAVEN_HOME environment variable is set and pointing to maven installation location.

To run the application, please perform following steps.
1. Clone the repository on local machine
2. Go to cloned repository folder on local machine using command prompt/terminal and run command "mvn package". Note: if build fails due to test cases in IntegrationTests.java, please make sure port 9999 is not already used by some other application. Alternative is to change the port mentioned in BASE_URI variable in IntegrationTests.java.
3. Go to target folder after completion of step 2 and run command "java -Dserver.port=8085 -jar numbertowords.jar". Value 8085 can be replaced by and port of your choice. -DServer.port variable is optional and if not provided by default server will start on port 8080.
4. Once application is running, application can be accessed via URL http://localhost:[server.port]/numbertoword/{numbertoconvert} via browser or using CURL "curl http://localhost:[server.port]/numbertoword/{numbertoconvert}" e.g. "curl http://localhost:8085/numbertoword/123"
5. To terminate the application press ctrl+c in command prompt/terminal.


