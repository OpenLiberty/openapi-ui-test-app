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

## Check Open Liberty modifications to Swagger UI are present
As a reference to the default use https://petstore.swagger.io/ 

### For the Default MicroProfile UI
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

## URL customization (Microprofile OpenAPI UI only)
- Stop the server with `./gradlew libertyStop`
- Add `<mpOpenAPI docPath="/foo />` to `server.xml`
- Start and deploy the server with `./gradlew libertyStart`
- Check the ui is available on https://localhost:9443/foo/ui
- check the expected customizations are still in place
- Stop the server with `./gradlew libertyStop`
- add `uiPath=/bar` to the `mpOpenAPI` definition in `server.xml`
- Change the redirect path in the oauth provider definition for `mp-ui` to be `https://localhost:9443/bar/oauth2-redirect.html`
- Start and deploy the server with `./gradlew libertyStart`
- Check the ui is available on https://localhost:9443/bar
- Authorize the UI using the `mp-ui` credentials for OAuth


Template checklist in markdown to add to PR to show status of manual testing
```markdown
Manual Testing results using output of build

## Open Liberty UI Customizations

### MicroProfile UI
- [ ] UI Loads on https://localhost:9443/openapi/ui/
- [ ] Forced reload causes all resources to be reloaded
- [ ] OpenLiberty logo is at the top of the page
- [ ] Top of the page has the title and description for the application but no links
- [ ] `OAS3` icon surrounding colour is `#5d8203` via element inspection
- [ ] Footer is present
- [ ] Footer contains links for `Terms of service`, `Test Author - Website`, `Contact Test Author` and `Example license`
- [ ] No `Valid` button in footer
- Operation Block colours:
   - [ ] POST background is `rgba(32,128,80,.1)` (div class = `opblock opblock-post`)
   - [ ] POST border is #208050` (div class = `opblock opblock-post`)
   - [ ] POST Icon background colour is `#208050` (div class = `opblock-summary-method`)
   - [ ] PUT background is `rgba(177,99,3,.1)` (div class = `opblock  opblock-put`)
   - [ ] PUT border colour is `#b16303` (div class=`opblock-summary-method`)
   - [ ] PUT Icon background colour is `#b16303` (div class=`opblock-summary-method`)
   - [ ] DELETE background is `rgba(224,7,7,.1)` (div class = `opblock  opblock-delete`)
   - [ ] DELETE border colour is `#e00707` (div class=`opblock-summary-method`)
   - [ ] DELETE Icon background colour is `#e00707` (div class=`opblock-summary-method`)
   - [ ] GET background is `rgba(31,111,240,.1)` (div class = `opblock  opblock-get`)
   - [ ] GET border colour is `#1f6ff0` (div class=`opblock-summary-method`)
   - [ ] GET Icon background colour is `#1f6ff0` (div class=`opblock-summary-method`)

### Openapi 3.x UI
Public UI
- [ ] UI loads https://localhost:9443/api/explorer/
- [ ] Forced reload causes all resources to be reloaded
- [ ] OpenLiberty logo is at the top of the page
- [ ] The Filter box is present at the top of the page
- [ ] Filter using `openapi-ui-test-app`  of prefixes of returns the operations
- [ ] Filter using anything other value returns no operations
- [ ] Top of the Page has `Liberty REST APIs`
- [ ] `OAS3` icon surrounding colour is `#5d8203` via element inspection
- [ ] No `Valid` button in page footer
- Operation Block colours:
   - [ ] POST background is `rgba(32,128,80,.1)` (div class = `opblock opblock-post`)
   - [ ] POST border is #208050` (div class = `opblock opblock-post`)
   - [ ] POST Icon background colour is `#208050` (div class = `opblock-summary-method`)
   - [ ] PUT background is `rgba(177,99,3,.1)` (div class = `opblock  opblock-put`)
   - [ ] PUT border colour is `#b16303` (div class=`opblock-summary-method`)
   - [ ] PUT Icon background colour is `#b16303` (div class=`opblock-summary-method`)
   - [ ] DELETE background is `rgba(224,7,7,.1)` (div class = `opblock  opblock-delete`)
   - [ ] DELETE border colour is `#e00707` (div class=`opblock-summary-method`)
   - [ ] DELETE Icon background colour is `#e00707` (div class=`opblock-summary-method`)
   - [ ] GET background is `rgba(31,111,240,.1)` (div class = `opblock  opblock-get`)
   - [ ] GET border colour is `#1f6ff0` (div class=`opblock-summary-method`)
   - [ ] GET Icon background colour is `#1f6ff0` (div class=`opblock-summary-method`)

Private UI
- [ ] UI load on https://localhost:9443/ibm/api/explorer/ having provided credentials outlined in README
- [ ] Forced reload causes all resources to be reloaded
- [ ] OpenLiberty logo is at the top of the page
- [ ] The Filter box is present at the top of the page
- [ ] Filter using `openapi-ui-test-app`  of prefixes of returns the operations
- [ ] Filter using anything other value returns no operations
- [ ] Top of the Page has `Liberty REST APIs`
- [ ] `OAS3` icon surrounding colour is `#5d8203` via element inspection
- [ ] No `Valid` button in page footer
- [ ] Operation Block colours:
   - [ ] POST background is `rgba(32,128,80,.1)` (div class = `opblock opblock-post`)
   - [ ] POST border is #208050` (div class = `opblock opblock-post`)
   - [ ] POST Icon background colour is `#208050` (div class = `opblock-summary-method`)
   - [ ] PUT background is `rgba(177,99,3,.1)` (div class = `opblock  opblock-put`)
   - [ ] PUT border colour is `#b16303` (div class=`opblock-summary-method`)
   - [ ] PUT Icon background colour is `#b16303` (div class=`opblock-summary-method`)
   - [ ] DELETE background is `rgba(224,7,7,.1)` (div class = `opblock  opblock-delete`)
   - [ ] DELETE border colour is `#e00707` (div class=`opblock-summary-method`)
   - [ ] DELETE Icon background colour is `#e00707` (div class=`opblock-summary-method`)
   - [ ] GET background is `rgba(31,111,240,.1)` (div class = `opblock  opblock-get`)
   - [ ] GET border colour is `#1f6ff0` (div class=`opblock-summary-method`)
   - [ ] GET Icon background colour is `#1f6ff0` (div class=`opblock-summary-method`)

## Functionality Testing

### MicroProfile UI
Access https://localhost:9443/openapi/ui
- [ ] Two Server values in Server dropdown
- Select `HTTPS` server url
- Expand `GET /{id}` operation
   - [ ] Two responses - `200` and `404` displayed
   - Change `media type` in drop down
      - [ ] Example changes from either JSON -> XML or XML->JSON
   - [ ]  Can switch between `Example Value` and `Schema`
   - [ ] Attempt to provide an ID value and should be prohibited from doing so
   - Click `Try Out`
      -  Provide an ID value of `1`
      - Click `Execute` and get a
         -  [ ] `200` response
      - Change ID value to `0`
      - Click `Execute`
      - [ ] `404` response
- Expand `POST /private`
   - [ ] Padlock Icon is `Unlocked`
   - Click `Try Out`
      - Click execute
      - [ ] `401` response
-  Click `Authorize` and go to `oauth` section
- Provide `Client_id` provided in README
- Provide `Client_secret` provided in README
- Select Test Scope
- Click `authorize`
   - Login with user credentials provided in README
   - click `allow once`
   - [ ] returned to the OpenAPI UI
- Close modal
- [ ] All `/private*` operations now have a locked padlock
- Expand `POST /private`
   - Click `Try Out`
   - [ ] Click `Execute` and get a `200` response
- [ ] Expand `GET /apiKeyNeeded`
   - [ ] Padlock is `unlocked`
   - [ ] Description is `Get a random record`
   - [ ] Click Try Out
      - [ ] Click Execute and get a `403` response
- Click `Authorize`
   - Enter apikey from README
   - Click `Authorize`
   - Close modal

### OpenAPI 3.x UI
Access https://localhost:9443/api/explorer
- [ ] Two Server values in Server dropdown
- Select `HTTPS` server url
- Expand `GET /{id}` operation
   - [ ] Two responses - `200` and `404` displayed
   - Change `media type` in drop down
      - [ ] Example changes from either JSON -> XML or XML->JSON
   - [ ]  Can switch between `Example Value` and `Schema`
   - [ ] Attempt to provide an ID value and should be prohibited from doing so
   - Click `Try Out`
      -  Provide an ID value of `1`
      - Click `Execute` and get a
         -  [ ] `200` response
      - Change ID value to `0`
      - Click `Execute`
      - [ ] `404` response
- Expand `POST /private`
   - [ ] Padlock Icon is `Unlocked`
   - Click `Try Out`
      - Click execute
      - [ ] `401` response
-  Click `Authorize` and go to `oauth` section
- Provide `Client_id` provided in README
- Provide `Client_secret` provided in README
- Select Test Scope
- Click `authorize`
   - Login with user credentials provided in README
   - click `allow once`
   - [ ] returned to the OpenAPI UI
- Close modal
- [ ] All `/private*` operations now have a locked padlock
- Expand `POST /private`
   - Click `Try Out`
   - [ ] Click `Execute` and get a `200` response
- [ ] Expand `GET /apiKeyNeeded`
   - [ ] Padlock is `unlocked`
   - [ ] Description is `Get a random record`
   - [ ] Click Try Out
      - [ ] Click Execute and get a `403` response
- Click `Authorize`
   - Enter apikey from README
   - Click `Authorize`
   - Close modal
- Expand `/apiKeyNeeded`
   - [ ] Operation has a locked padlock
   - Click `Try Out`
      - Click `Execute`
      -  [ ] `200` response

## Customer provided customization

MicroProfile UI
- If the server is running, stop the server
- Rename the folder `example-customization` in `src/main/liberty/config` to `mpopenapi`
- Start the server
- Access https://localhost:9443/openapi/ui/
   - Perform a forced page refresh
   - [ ] Header bar colour has changed
   - [ ] Logo has changed

OpenAPI-3.x UI
- If the server is running, stop the server
- Rename the folder `example-customization` (or mpopenapi if renamed for MicroProfile testing) in `src/main/liberty/config` to `openapi-3.1`
- Start the server
- Access https://localhost:9443/api/explorer
   - Perform a forced page refresh
   - [ ] Header bar colour has changed
   - [ ] Logo has changed

## Endpoint customization
(Microprofile only)

- Update mpOpenAPI config with new `docPath`
- Start server
- Access https://localhost:9443/<docpath>/ui
- [ ] Able to see page
- [ ] Page shows api operations
- [ ] Default UI customizations are in place
- Update mpOpenAPI config with different `uiPath`
- Update Oauth Provider config redirect path with uiPath value
- Start server
- Access https://localhost:9443/<uiPath>
- [ ] Able to see page
- [ ] Page shows api operations
-  Click `Authorize` and go to `oauth` section
- Provide `Client_id` provided in README
- Provide `Client_secret` provided in README
- Select Test Scope
- Click `authorize`
   - Login with user credentials provided in README
   - click `allow once`
   - [ ] returned to the OpenAPI UI
- Close modal
```

[swagger-ui]: https://github.com/swagger-api/swagger-ui
[swagger-ui-download]: https://github.com/swagger-api/swagger-ui/tree/master/dist
[swagger-ui-online]: https://petstore.swagger.io
[oas-badge-liberty]: images/oas-badge-liberty.png "Liberty OAS badge"
[oas-badge-original]: images/oas-badge-original.png "Original Swagger UI OAS badge"
[op-color-liberty]: images/op-color-liberty.png "Liberty operation colours"
[op-color-original]: images/op-color-original.png "Original Swagger UI operation colours"
