# Collection Runner

## Intro

Collection Runner is a Java/Spring Boot "framework" that aims at emulating the "collection runner" feature of Postman. </br>
No GUI is provided, all configuration is done by writing simple Java classes for requests and "scripts".

## Main classes

### CollectionRunner

Is the main class, used for emulating the collection-runner feature.
It performs the following steps:

1. Execute pre-request scripts
2. Build a Request
3. Execute the Request
4. Verify if the Request should be re-triggered
5. Execute post-request scripts

Method chain(CollectionRequest request) is used to chain requests one after the other.

### CollectionRequest

Is the class representing a generic request that CollectionRunner can run: </br>
all requests should extend this basic request implementing its abstract methods.
Two methods worth mentioning:

1. preExecutionScript: executes before sending the request
2. postExecutionScript: executes after receiving a response

This two methods have access to the surrounding context to set/get variables as needed.

### Tasks

Collection Runner supports the scheduling of recurring tasks using @Scheduled annotation. </br>
For an example, look into ScheduledTask.

## Usage

Look into CollectionRunnerTest for an example on how to use Collection Runner.

## Credits

Francesco Cardillo
