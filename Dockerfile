FROM openjdk
COPY build/libs/employee-service-0.0.1-SNAPSHOT.jar /employee-service.jar
EXPOSE 8080
CMD java -jar employee-service.jar