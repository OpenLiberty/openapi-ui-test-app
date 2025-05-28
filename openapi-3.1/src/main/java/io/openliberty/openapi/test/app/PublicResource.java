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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

@Tag(name = "public")
@Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PublicResource {
    
    @Inject
    private RecordStore records;
    
    @GET
    @Path("/{id}")
    @APIResponse(responseCode = "404", description = "No record with that id available")
    @APIResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExampleRecord.class)),
            @Content(mediaType = MediaType.APPLICATION_XML, schema = @Schema(implementation = ExampleRecord.class)),
    })
    public ExampleRecord get(@Schema(description = "The record ID") @PathParam("id") int id) {
        ExampleRecord result = records.get(id);
        if (result == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
        return result;
    }
    
    @POST
    public ExampleRecord post(@Schema(description = "The record to add") ExampleRecord record) {
        records.store(record);
        return record;
    }
    
    @GET
    @Operation(description = "Return all records")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ExampleRecord> getAll() {
        return records.getAll();
    }
    
    @PUT
    @Path("/{id}")
    public ExampleRecord update(@PathParam("id") int id, ExampleRecord record) {
        record.id = id;
        records.store(record);
        return record;
    }
    
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) {
        records.delete(id);
    }
}
