# sevensenderschallenge

Steps to run the project:

1. Make sure you have docker and docker-compose installed in your linux system
2. Pull the project from git in your local system and from commandline go to it's folder location
3. "sudo docker-compose build" to build the project
4. "sudo docker-compose up" to run the Spring Application in local
5. The Application will be accessible in the URL "http://localhost:8080/strips"


Details on Project:

The project exposes a RESTFUL WebService which pulls the latest 10 strips from each of the public Api
"https://xkcd.com/json.html", "http://feeds.feedburner.com/PoorlyDrawnLines" and combines them in a single json feed (20 recent entries in total). 
The response should contain the following data for each entry:
- picture url
- title / description
- web url for browser view
- publishing date

Sort the resulting feed by publishing date from recent to older.


Steps to explicitly run a test:

1. For explicitly running the tests you need to import the project into intellij IDE
2. Then run the test present under src/main/test
