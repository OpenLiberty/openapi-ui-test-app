# Liberty OpenAPI UI Test application

Application and server configuration for testing the liberty OpenAPI UI.

The Liberty OpenAPI UI is an extended version of [Swagger UI][swagger-ui] which makes a few adjustments. This test procedure tests that our modifications are present and that we haven't obviously broken anything.

# Test Procedure

## Setup

 - Build liberty locally with the changes you want to test
 - Clone this repository
 - In `build.gradle` update `installDir` to point to the `build.image/wlp` directory in your workspace so that the test runs on your locally built liberty
   - If you need to test against a released version of liberty, you can comment out `installDir` and uncomment the `dependencies` section instead
 - start the server and deploy the test application by running `./gradlew libertyStart`

## Check our modifications to Swagger UI are present

### For the MicroProfile UI
 - Open the MP UI in your browser: https://localhost:9443/openapi/ui/
 - Hold Shift and click the reload button to ensure that all resources are reloaded
 - Check the OpenLiberty logo is at the top of the page
 - Check there is no filter bar at the top of the page
 - Check the top of the page has the title and description for the application but no links
 - Check the colour for the OAS badge next to the title
   - This is subtle, it should be #5d8203 (original is #89bf04)
   - Correct: ![oas-badge-liberty]
   - Incorrect: ![oas-badge-original]
 - Check the footer is present and has links for TOS, author website, author e-mail, license
 - Check there is no validation button in the footer
 - Check the colours for GET, POST, PUT and DELETE operations
   - Correct: ![op-color-liberty]
   - Incorrect: ![op-color-original]

### For the openapi-3.x UI
 - Open the openapi UI in your browser: https://localhost:9443/api/explorer/
 - Hold Shift and click the reload button to ensure that all resources are reloaded
 - Check the OpenLiberty logo is at the top of the page
 - Check the filter bar is present at the top of the page
   - Check that filtering for openapi-ui-test-app (or any prefix) works
   - Check that filtering for anything else gives no operations
 - Check that the top of the page has "Liberty REST APIs"
 - Check the OAS badge colour
 - Check there is no validation button in the footer
 - Check the colours for GET, POST, PUT and DELETE operations
 - Repeat the above steps for https://localhost:9443/ibm/api/explorer/
   - This page needs authentication. Log in with testuser/testpassword.

## Check basic functionality
 - Repeat this for both the MicroProfile UI and the openapi-3.x UI
 - As you do the test, look out for anything which looks broken
   - If you're not sure if something is wrong, you can compare with the base swagger-ui [download][swagger-ui-download], [online][swagger-ui-online].
 - In the severs drop-down, select the https server
   - Your browser may not let you make requests to an http server from an https page
 - Open `GET /{id}` operation
   - Check that there are two responses (200 and 404)
   - Check that there are two media types for a 200 response
   - Check that when you change the media type drop down, the example below changes
   - Check that you can switch between the Example Value and Schema
   - Click "Try it out" and retrieve id 1
   - Retrieve id 0 and check that you get a 404
 - Open `POST /private` operation
   - Check that the bar has an unlocked padlock icon on the right
   - Execute the method using Try it out
   - Check that you get a 401 or 403 response
 - Click Authorize at the top of the page and login with oauth
   - For the oauth login, it's important that you use https is used to access the UI and that the hostname is "localhost"
   - For the openapi-3.x UI, use clientid: openapi-ui, secret: abc
   - For the MicroProfile UI, use clientid: mp-ui, secret: abc
   - Select the "test" scope
   - Click "authorize" (this should open a new page with a login box)
   - log in with testuser/testpassword
   - click "allow once"
   - you should now be logged in 
     - an Auth Warning message may be displayed saying that "Authorization may be unsafe", this is an existing issue
   - Click close
 - Check that `POST /private` now has a locked padlock icon
   - Execute the method using Try it out
   - This time it should succeed with a 200 response
 - Open the `GET /apiKeyNeeded` operation
   - Check that the bar has an unlocked padlock icon on the right
   - Check that the description for the operation is "Get a random record"
   - Execute the method using Try it out
   - Check that you get a 403 response code
 - Click Authorize at the top of the page
   - Under apikey enter "12345"
   - you should now be logged in
   - Click close
 - Check that `GET /apiKeyNeeded` now has a locked padlock icon
   - Execute the method using Try it out
   - This time it should succeed with a 200 response

## Check customizations
 - Stop the server with `./gradlew libertyStop`
 - in `src/main/liberty/config`, rename the `example-customization` directory to `mpopenapi`
 - Start and deploy the server with `./gradlew libertyStart`
 - Check the headerbar colour and logo has changed on https://localhost:9443/openapi/ui/ (may need to Shift-refresh)
 - Stop the server with `./gradlew libertyStop`
 - in `src/main/liberty/config`, rename the `mpopenapi` directory to `openapi-3.1`
 - Start and deploy the server with `./gradlew libertyStart`
 - Check the headerbar colour and logo has changed on https://localhost:9443/api/explorer/ (may need to Shift-refresh)

[swagger-ui]: https://github.com/swagger-api/swagger-ui
[swagger-ui-download]: https://github.com/swagger-api/swagger-ui/tree/master/dist
[swagger-ui-online]: https://petstore.swagger.io
[oas-badge-liberty]: images/oas-badge-liberty.png "Liberty OAS badge"
[oas-badge-original]: images/oas-badge-original.png "Original Swagger UI OAS badge"
[op-color-liberty]: images/op-color-liberty.png "Liberty operation colours"
[op-color-original]: images/op-color-original.png "Original Swagger UI operation colours"
