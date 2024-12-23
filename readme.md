# URL Shortener
Spring Boot based REST API that takes a URL and returns a shortened URL.

# Getting Started
## Dependencies
This project depends on

- spring-boot-starter-web (Spring boot framework)
- commons-validator:1.9.0 (for URL validation)
- spring-boot-starter-test (for tests)
- mockito-core (for tests)

## Project Build
To build this project, run
```shell
git clone https://github.com/Devendra274/URL-Shortener.git
cd url-shortener
mvn clean install
```
The jar will be generated under target folder 

## Docker Deployment
Project image can be generated using Dockerfile. To create docker image:
```shell
docker build -t urlshortener_dev:v2 .
```

To push image to dockerhub
```shell
docer login
docker tag urlshortener_dev:v2 devendra274/url-shortener:v2
docker push devendra274/url-shortener:v2
```

You can download the image and run it on your system
```shell
docker pull devendra274/url-shortener:v2
docker run -d -p 8080:8080 devendra274/url-shortener:v2
docker ps
```
The application will be accessible on http://localhost:8080

To stop the container
```shell
docker stop url-shortener
```

# API Endpoints
You can access following API endpoints at http://localhost:8080

## POST /shorten
It takes a JSON object in the following format as payload
```shell
{
  "url":"<The URL to be shortened>"
}
```
### Example
```shell
cURL
curl -X POST \
http://localhost:8080/api/shorten \
-H 'Content-Type: application/json' \
-d '{"url":"https://example.com/sample"}'
Response:

{
"shortUrl": "<shortened url for the original url provided in the request payload>"
}
```

## GET /redirect/{shortened}
It takes a Path Param string which is the shorten hash to redirect to the original url.
### Example
```shell
cURL
curl -X POST \
http://localhost:8080/api/redirect/d5yU8q \
-H 'Content-Type: application/json'
```
Response is basically redirecting (i.e. Http Status code 302) to the fetched original url
```java
ResponseEntity.status(302).header("Location", originalUrl).build();
```

## GET /metrics
It takes a Path Param string which is the shorten hash to redirect to the original url.
### Example
```shell
cURL
curl -X POST \
http://localhost:8080/api/metrics \
-H 'Content-Type: application/json'
```
In response it fetches top 3 domains.

# Future Enhancements / Known Issues
- Since the project is for demo purpose only, the url are stored in memory. We can us database ideally to store the shorten hash, the domain, the original url and the created date and time too. Date and time would help at time of metrics calculations too. 
- Database can have multiple shards on basis of domain series and most popular domain which are popularly fetched frequently.
- Implement cache 
- We can have a docker-compose.yml configuring all the services like database n cache with volumes. Also have separate Dockerfile for api, db etc. 
- Implement https
