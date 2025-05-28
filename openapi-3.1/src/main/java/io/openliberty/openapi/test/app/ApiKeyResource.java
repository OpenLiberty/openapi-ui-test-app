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

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

@SecurityRequirement(name = "apikey")
@Tag(name = "private")
@Path("/apiKeyNeeded")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class ApiKeyResource {
    
    @Inject
    private RecordStore records;
    
    @Operation(description = "Get a random record")
    @GET
    public ExampleRecord getRandom(@QueryParam("apiKey") @Parameter(hidden = true) String apiKey) {
        if (!"12345".equals(apiKey)) {
            throw new WebApplicationException("Invalid API Key", Status.FORBIDDEN);
        }
        Random r = ThreadLocalRandom.current();
        List<ExampleRecord> allRecords = records.getAll();
        int i = r.nextInt(allRecords.size());
        return allRecords.get(i);
    }

}
