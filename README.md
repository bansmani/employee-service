# employee-service
Employee Service provide Employee registration feature, 
along with employee listing and basic search using employee ID.

Application is developed using Kotlin with Spring boot application with pure TDD practice. 

Note: this is only a backend RESTfull API and does not contain UI project 
  

#### Building and Running application 
clone repository to your local machine and switch inside project root
 and use gradle command 
 
 ```json
Windows: 
 gradlew bootRun

Unix: 
gradle bootRun
```
building code
```json
gradlew build
```

running directly the build with java command 
```json
java -jar employee-service-0.0.1-SNAPSHOT.jar
```

Alternatively you can import project in your favourite IDE, 
preffered Intellij Idea due to kotlin dependency, and execute from there

Swagger documentation URL : http://localhost:8080/swagger-ui/index.html

#### Input Json Schema
```json
{
  "empId": "string",
  "firstName": "string",
  "lastName": "string",
  "gender": "MALE",
  "dob": "2020-07-29",
  "department": "string",
  "action": "CREATE"
}
```

#### Development Practice
Application is Developed using Pure TDD practice

* REST Api layer - Integration testing 
* Controller layer and Service Layer is tested with Mocks  
* Repository Layer - Integration testing 

Note: Application is using In-Memory H2 database for testing purpose, 
for production add applicable database driver and configuration in spring property file 
 
#### Features: 
- Registering new Employee or updating existing employee 
- List employee uisng pagination 
- Sorting feature
- find employee using (limited to emp id)
- basic validation 
- exception handling 
- logging (limited)
- swagger documentation 
- Test automation (TDD) including unit and integration coverage
- Idempotent service 


#### Future Roadmap : 
- Criteria search 
- JQL based data filter and search 
- Creating department service remove direct department reference from Employee Service
- Spring profiling
- more data validation 
- security: auth, ssl  
- enhanced logging 
- Dockerfile, Jenkinsfile, jacoco coverage, sonar  

