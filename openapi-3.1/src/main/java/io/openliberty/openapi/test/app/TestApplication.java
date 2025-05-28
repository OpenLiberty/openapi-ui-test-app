/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * SPDX-License-Identifier: EPL-1.0
 *******************************************************************************/
package io.openliberty.openapi.test.app;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.PathItem;
import org.eclipse.microprofile.openapi.annotations.PathItemOperation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.OAuthScope;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Test OpenAPI app",
                                description = "This is an app for testing many of the features of liberty's OpenAPI support",
                                summary = "It's just a test app",
                                version = "1.0",
                                contact = @Contact(name = "Test Author",
                                                   email = "tauthor@example.com",
                                                   url = "http://example.com/tauthor"),
                                license = @License(name = "Apache 2.0",
                                                   identifier = "Apache-2.0"),
                                termsOfService = "http://example.com/tos"),
                   webhooks = @PathItem(name = "event",
                                        description = "Sent whenever a CRUD event occurs",
                                        operations = @PathItemOperation(method = "post",
                                                                        requestBody = @RequestBody(content = @Content(mediaType = "application/json",
                                                                                                                      schema = @Schema(ref = "ExampleRecord"))))),
                   externalDocs = @ExternalDocumentation(description = "Docs", url = "http://example.com/docs"))
@SecurityScheme(securitySchemeName = "apikey", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.QUERY,
                apiKeyName = "apiKey")
@SecurityScheme(securitySchemeName = "oauth", type = SecuritySchemeType.OAUTH2,
                flows = @OAuthFlows(authorizationCode = @OAuthFlow(authorizationUrl = "https://localhost:9444/oauth2/endpoint/SampleProvider/authorize",
                                                                   tokenUrl = "https://localhost:9444/oauth2/endpoint/SampleProvider/token",
                                                                   scopes = @OAuthScope(name = "test"))))
@ApplicationPath("/")
public class TestApplication extends Application {

    @Inject
    private RecordStore records;

    @PostConstruct
    private void testDataSetup() {
        records.store(new ExampleRecord(1, "Dancing Rat"));
        records.store(new ExampleRecord(2, "Cheeseboard"));
    }

}
