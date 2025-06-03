# Liberty OpenAPI UI Test application

Application and server configuration for testing the liberty OpenAPI UI.

The Liberty OpenAPI UI is an extended version of [Swagger UI][swagger-ui] which makes a few adjustments. This test procedure tests that our modifications are present and that we haven't obviously broken anything.

If something looks wrong, it can be useful to compare how it's rendered in the online [Swagger UI editor][swagger-ui-editor].

## Test Procedure

### Setup

- Java 17 or later is required
- Build liberty locally with the changes you want to test
- Clone this repository
- In `build.gradle` update `installDir` to point to the `build.image/wlp` directory in your workspace so that the test runs on your locally built liberty
  - If you need to test against a released version of liberty, you can comment out `installDir` and uncomment the `dependencies` section instead
- start the server and deploy the test application by running `./gradlew libertyStart`

### MicroProfile UI (OpenAPI 3.0)

- [ ] UI Loads on https://localhost:9443/openapi/ui/
- [ ] Forced reload causes all resources to be reloaded
- [ ] OpenLiberty logo is at the top of the page
- [ ] No filter bar at the top of the page
- [ ] Top of the page has the title and description for the application but no links
- [ ] `OAS3` icon surrounding colour is `#5d8203` via element inspection
- [ ] Footer is present
- [ ] Footer contains links for `Terms of service`, `Test Author - Website`, `Contact Test Author`, `Example license` and `Docs`
- [ ] No `Valid` button in footer
- Operation Details
  - Expand `GET /{id}`
    - [ ] `id` parameter is marked `* required`

### MicroProfile UI (OpenAPI 3.1)

- [ ] UI Loads on https://localhost:9444/openapi/ui/
- [ ] Forced reload causes all resources to be reloaded
- [ ] OpenLiberty logo is at the top of the page
- [ ] No filter bar at the top of the page
- [ ] Top of the page has the title and description for the application but no links
- [ ] `OAS3.1` icon surrounding colour is `#5d8203` via element inspection
- [ ] Footer is present
- [ ] Footer contains links for `Terms of service`, `Test Author - Website`, `Contact Test Author`, `Apache 2.0` and `Docs`
  - [ ] Apache 2.0 link works
- [ ] No `Valid` button in footer
- [ ] Webhooks section below path operations is present
  - [ ] `POST event` operation is listed as a webhook
- [ ] Two Server values in Server dropdown
- Select `HTTPS` server url
- Expand `GET /{id}` operation
  - [ ] `id` parameter is marked `* required`
  - [ ] Two responses - `200` and `404` displayed
  - Change `media type` in drop down
    - [ ] Example changes from either JSON -> XML or XML->JSON
  - [ ]  Can switch between `Example Value` and `Schema`
  - [ ] Attempt to provide an ID value and should be prohibited from doing so
  - Click `Try Out`
    - Provide an ID value of `1`
    - Click `Execute` and get a
      - [ ] `200` response
    - Change ID value to `0`
    - Click `Execute`
      - [ ] `404` response
- Expand `POST /private`
  - [ ] Padlock Icon is `Unlocked`
  - Click `Try Out`
    - Click execute
      - [ ] `401` response
- Click `Authorize` and go to `oauth` section
- Provide `Client_id` `mp-ui`
- Provide `Client_secret` `abc`
- Select Test Scope
- Click `authorize`
  - Login with user credentials `testuser`/`testpassword`
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
  - Enter apikey `12345`
  - Click `Authorize`
  - Close modal
- Expand `GET /apiKeyNeeded`
  - [ ]  Operation has a locked padlock
  - Click Try Out
  - Click Execute
    - [ ] 200 response

### `openapi-3.x` Public UI

- [ ] UI loads https://localhost:9443/api/explorer/
- [ ] Forced reload causes all resources to be reloaded
- [ ] OpenLiberty logo is at the top of the page
- [ ] The Filter box is present at the top of the page
- [ ] Filter using `openapi-ui-test-app` (or any prefix of that) returns the operations
- [ ] Filter using any other value returns no operations
- [ ] Top of the Page has `Liberty REST APIs`
- [ ] `OAS3` icon surrounding colour is `#5d8203` via element inspection
- [ ] No `Valid` button in page footer
- [ ] Two Server values in Server dropdown
- Select `HTTPS` server url
- Expand `GET /{id}` operation
  - [ ] Two responses - `200` and `404` displayed
  - Change `media type` in drop down
    - [ ] Example changes from either JSON -> XML or XML->JSON
  - [ ]  Can switch between `Example Value` and `Schema`
  - [ ] Attempt to provide an ID value and should be prohibited from doing so
  - Click `Try Out`
    - Provide an ID value of `1`
    - Click `Execute` and get a
      - [ ] `200` response
    - Change ID value to `0`
    - Click `Execute`
      - [ ] `404` response
- Expand `POST /private`
  - [ ] Padlock Icon is `Unlocked`
  - Click `Try Out`
    - Click execute
    - [ ] `401` response
- Click `Authorize` and go to `oauth` section
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
    - [ ] `200` response

### `openapi-3.x` Private UI

- [ ] UI loads on https://localhost:9443/ibm/api/explorer/ with credentials `testuser`/`testpassword`
- [ ] Forced reload causes all resources to be reloaded
- [ ] OpenLiberty logo is at the top of the page
- [ ] The Filter box is present at the top of the page
- [ ] Filter using `openapi-ui-test-app` (or any prefix of that) returns the operations
- [ ] Filter using any other value returns no operations
- [ ] Top of the Page has `Liberty REST APIs`
- [ ] `OAS3` icon surrounding colour is `#5d8203` via element inspection
- [ ] No `Valid` button in page footer

### Test customizations

- Test for mpOpenAPI (OpenAPI 3.0)
  - Stop the server: `./gradlew :openapi-3.0:libertyStop`
  - Copy in the customization: `cp -r openapi-3.0/src/main/liberty/config/example-customization openapi-3.0/src/main/liberty/config/mpopenapi`
  - Start the server: `./gradlew :openapi-3.0:libertyStart`
  - [ ] Headerbar colour and logo has changed on https://localhost:9443/openapi/ui/ (may need to Shift-refresh)
- Test for mpOpenAPI (OpenAPI 3.0)
  - Stop the server: `./gradlew :openapi-3.1:libertyStop`
  - Copy in the customization: `cp -r openapi-3.1/src/main/liberty/config/example-customization openapi-3.1/src/main/liberty/config/mpopenapi`
  - Start the server: `./gradlew :openapi-3.1:libertyStart`
  - [ ] Headerbar colour and logo has changed on https://localhost:9444/openapi/ui/ (may need to Shift-refresh)
- Test for `openapi-3.x` feature
  - Stop the server: `./gradlew :openapi-3.0:libertyStop`
  - Copy in the customization: `cp -r openapi-3.0/src/main/liberty/config/example-customization openapi-3.0/src/main/liberty/config/openapi-3.1`
  - Start the server: `./gradlew :openapi-3.0:libertyStart`
  - [ ] Headerbar colour and logo has changed on https://localhost:9443/api/explorer/ (may need to Shift-refresh)

### Shutdown

- Stop both servers with `./gradlew libertyStop`

[swagger-ui]: https://github.com/swagger-api/swagger-ui
[swagger-ui-editor]: https://editor-next.swagger.io/
