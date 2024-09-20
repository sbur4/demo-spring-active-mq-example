1. Docker:
- image-> rmohr/activemq:latest
- port-> 61616:61616 / 8161:8161
- http://localhost:61616/admin/
- 
2. How to run:
   + Application run as debug
   - gradle build -x test
   - in git bash ./gradlew build -x test

3. http://localhost:8080/send?message=abc
4. http://localhost:8080/sendAndReceive?message=aaaaA



