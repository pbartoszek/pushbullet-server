# Pushbullet Server 

### Starting the application
To start the application please run the following maven command from command line:

```sh
mvn spring-boot:run
```

Application will be available under localhost on port 8080.

### Registering a new user

In order to register a new user please make a POST request to

<http://localhost:8080/users>

with the following body:

```json
{"username": "bbcUser1", "accessToken": "casdw3f3rf33XCdV3"}
```
  
server should return response:
```json
{
  "accessToken": "casdw3f3rf33XCdV3",
  "username": "bbcUser1",
  "numOfNotificationsPushed": 0,
  "creationTime": "2017-04-10T22:40:15"
}
```

#### Validation logic
Username and accessToken are mandatory. Server will check if username and token are unique among registered users.
If validation fails an error response is returned ie:

```json
{
  "message": "User already exists",
  "errorCode": "user_already_exists"
}
```

### Returning all registered users

In order to return all registered users please make a GET request to

<http://localhost:8080/users>

to server should return all users ordered by created time 

```json
[
  {
    "accessToken": "casdw3f3rf33XCdV3",
    "username": "bbcUser1",
    "numOfNotificationsPushed": 0,
    "creationTime": "2017-04-10T22:40:15"
  }
]
```

### Sending a push

In order to send a push please make a POST request to

<http://localhost:8080/push>

with the following body:

```json
{"username": "bbcUser1", "title": "Breaking news!", "body": "A new president has been elected...."}
```
  
server should return response:
```json
{
  "pushId": "ujvU2XuBScmsjAy5TxvPyK",
  "numOfNotificationsPushed": 1
}
```
should PushBullet API return an error the error code and the message will be returned not changed in the response:

```json
{
  "message": "Access token is missing or invalid.",
  "errorCode": "invalid_access_token"
}
```
 
  