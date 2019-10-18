# springboot-basic-rest		
A minimalist example of REST layer in Spring Boot showing how to capture various parts of Http request like query parameters, 
request headers, path parameters, form parameters, request body etc.
		
It also illustrates testing of application's REST layer using Junit, Hamcrest and [JsonPath](https://github.com/json-path/JsonPath).
		
## Running application locally
```
git clone https://github.com/viralharia/springboot-basic-rest.git
cd springboot-basic-rest
mvnw spring-boot:run
```
		
## Running tests
```
mvnw test
```

## List of APIs
```
{GET /api}
{POST /api, consumes [application/x-www-form-urlencoded]}
{GET /api/pathparams/{pathParamName}/}
{POST /api, consumes [application/json], produces [application/json]}
{GET /api/queryparams}
{GET /api/requestheaders}
```

## Contributing/ToDos
1. Integrating Swagger UI
2. Creating POSTMAN collection json file to test all REST services
3. Supporting JSON and XML output both
4. Authentication apis
5. Deploy the application on AWS
6. setup Travis CI
