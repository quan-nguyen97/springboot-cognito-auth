# Secure Spring Boot resource server

This is an example, which demonstrates how you can secure your APIs on a spring boot resource server by verifying Oauth2 Access Token from AWS Cognito.

## Use case

* You created an AWS Cognito User pool.

* You have a frontend application, which pulls data from your spring boot resource server.
* The frontend application is authenticated by AWS Cognito and get an access token.
* Your spring boot server has to authenticate and authorize the request from frontend application by checking access token.
* Secure server API base on user group (user's groups are listed in claim `cognito:groups`)

## Key values

* Configure Spring Boot to use AWS Cognito for authenticating your API Call.
* Convert jwt token provided by AWS Cognito, so that Spring Boot App can check the authorities base on Cognito user's groups (class `CognitoAuthConfig` )

In this example I try to read claim `cognito:groups` and add it as Authorities. 
You can also convert any other claim (such as `role`) to Authority.

## Test

1. Create an admin group in Cognito User Pool. This group should be named as `admin`.
2. Create two different users and assign admin group to one user.
3. Set up correct `issuer-uri` in your `application.properties`.
You just have to set `aws-zone` and `user-pool-id` in the placeholder.
4. Run Spring boot application and try to get the endpoint `/test` and `/test/admin` 
with the tokens you get from Cognito after you authenticated your users.

## Support 

Support me to be a better developer.
I'm glad to hear any suggestion for improvement. 
Also leave me a star if you like this. 

If you have any question, feel free contact me via Email
`hquan.nguyen1997@gmail.com`