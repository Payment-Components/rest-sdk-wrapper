# Financial Messages REST APIs Wrapper
Payment Components is known for the Financial Messaging Solutions it provides for over 30 years.  
The Financial Messaging Libraries are written in Java. They can be integrated smoothly in a Java Application.  
In order to help developers of other Programming Languages worlds, we prepared this standalone REST API wrapper for MT, SEPA, MX(ISO20022), SWIFT Translator and CBPR+ libraries.

## Installation
It's a simple java web application written in spring boot. You can start it by running the following command `java -jar rest-sdk-wrapper-X.X.X.jar` or
you can run it inside a docker container using the following instructions:

1. Create a folder named `rest-sdk-wrapper`
2. Copy the jar inside this folder
3. Create a file named `Dockerfile` or use [this one](Dockerfile) and paste in it the following content
```Dockerfile
FROM openjdk:8-alpine
COPY ./*.jar /tmp/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/tmp/app.jar"]
```
4. Run the following command to build the docker container `docker build -t paymentcomponents/rest-sdk-wrapper .`
5. Run the following command to run the docker
   container `docker run -p 8080:8080 -n rest-sdk-wrapper paymentcomponents/rest-sdk-wrapper`

## Dependencies
It has a dependency to
- Swift MT SDK
- SEPA SDK
- ISO20022 SDK
- Swift Translator SDK

By default REST SDK Wrapper is using demo versions of the above SDKs. With the demo versions of the above SDKs you can do the following :

- Parse and validate an `MT101` using the `/mt/validate` or the `/mt/parse` endpoints
- Parse and validate a SEPA `PACS.002.001.03` using the `/sepa/validate` endpoint
- Parse and validate an ISO20022 `PACS.009.001.04` using the `/mx/validate` endpoint
- Translate an ISO20022 `PACS.009.001.08` to `MT200` for `CBPR+` using the `/swift/translator/mx/to/mt` endpoint
- Translate an `MT200` to ISO20022 `PACS.009.001.08` for CBPR+ using the `/swift/translator/mt/to/mx` endpoint
- Validate a CBPR+ `PACS.009.001.08` using the `/cbpr/validate` endpoint
- Validate an RTGS `PACS.009.001.08` using the `/rtgs/validate` endpoint 

## Features available in the paid versions of the SDKs

### The following MT operations are available
- Parse an MT message
- Validate an MT message
- Construct an MT103 from JSON
- Construct general MT message from JSON
- Create Universal Confirmation for MT103

### The following SEPA operations are available
- Validate a SEPA message and return it as JSON
- Create Payment Return for a pacs.008
- Create Cancellation Request for a pacs.008
- Create Resolution Of Investigation for a pacs.008

### The following ISO20022(MX) operations are available
- Validate an MX message and return it as JSON

### The following SWIFT Translator operations are available
- Translate an MT Message to the equivalent MX
- Translate an MX Message to the equivalent MT

### The following CBPR+ operations are available
- Validate a CBPR+ message
- Envelope a CBPR+ message inside a RequestPayload for FINplus service

### The following TARGET2(RTGS) operations are available
- Validate a TARGET2 message and return it as JSON


## Get Started
After following the steps described in [Installation](#installation), the app should run, by default, in the `8080` port.

### Swagger
A Swagger UI is available in {HOST}:{POST}/swagger-ui.html  
A Swagger json is available in {HOST}:{POST}/v3/api-docs/All%20APIs  
You can import it in Postman and try the APIs

### Log-id
In every request, a `log-id` is created and you can track your request with this.  
You can obtain the `log-id` from the `Response Headers`.
