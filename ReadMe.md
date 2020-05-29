# HTTP Server
Currently, it acts as an echo server, but plans are to add further features that will be documented here.

## Getting Started
1. Clone repo
2. Make sure you have [Java SDK](https://www.oracle.com/java/technologies/javase-downloads.html) and Gradle installed
    - This was built with JDK 14.0.1 and Gradle 6.4.1
3. Run the program (this will build the program as well)
    ```
    $ gradle run
    ```
4. Send a request to `localhost:3000`
    ```
    $ curl localhost:3000
    ```
   
## Run Tests
Tests were written using JUnit5. Use the following command to run tests:

```
$ gradle test -i
```

