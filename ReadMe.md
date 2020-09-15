# HTTP Server
This server is hosted on AWS, [here](http://httpserver-env-1.eba-eac65tjd.us-east-1.elasticbeanstalk.com/).
The list of routes can be found on the homepage.

## Getting Started
1. Clone repo
2. Make sure you have [Java SDK](https://www.oracle.com/java/technologies/javase-downloads.html) and Gradle installed
    - This was built with JDK 14.0.1 and Gradle 6.4.1
3. Run the program (this will build the program as well)
    ```
    $ gradle run
    ```
4. Send a request to `localhost:5000`
    ```
    $ curl localhost:5000
    ```
   
## Run Tests
Tests were written using JUnit5. Use the following command to run tests:

```
$ gradle test -i
```

