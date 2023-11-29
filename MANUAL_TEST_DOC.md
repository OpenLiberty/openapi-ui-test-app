# Open Liberty OpenAPI UI Testing 
This document lays out the steps and checks to perform to perform a manual validation of the OpenAPI UI provided via the OpenAPI and MicroProfile OpenAPI features. 

For general testing any browser can be used. However if doing an update to Swagger-UI version, IE11 testing must be performed alongside a proper browser

Copy from below this line:

----

Browser and version tested on: 

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
    - [ ] POST border is `#208050` (div class = `opblock opblock-post`)
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
- Operation Details
    - Expand `GET /{id}
      - [ ] `id` file is marked `* required`
    

### Openapi 3.x UI
Public UI
- [ ] UI loads https://localhost:9443/api/explorer/
- [ ] Forced reload causes all resources to be reloaded
- [ ] OpenLiberty logo is at the top of the page
- [ ] The Filter box is present at the top of the page
- [ ] Filter using `openapi-ui-test-app`  of prefixes of returns the operations
- [ ] Filter using any other value returns no operations
- [ ] Top of the Page has `Liberty REST APIs`
- [ ] `OAS3` icon surrounding colour is `#5d8203` via element inspection
- [ ] No `Valid` button in page footer
- Operation Block colours:
    - [ ] POST background is `rgba(32,128,80,.1)` (div class = `opblock opblock-post`)
    - [ ] POST border is `#208050` (div class = `opblock opblock-post`)
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
- [ ] Filter using any other value returns no operations
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
- Expand /apiKeyNeeded
    - [ ]  Operation has a locked padlock
    - Click Try Out
    - Click Execute
      - [ ] 200 response


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
- Rename the folder ` example-customization` in `src/main/liberty/config` to `mpopenapi`
- Start the server
- Access https://localhost:9443/openapi/ui/
    - Perform a forced page refresh
    - [ ] Header bar colour has changed
    - [ ] Logo has changed

OpenAPI-3.x UI
- If the server is running, stop the server
- Rename the folder ` example-customization` (or mpopenapi if renamed for MicroProfile testing) in `src/main/liberty/config` to `openapi-3.1`
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