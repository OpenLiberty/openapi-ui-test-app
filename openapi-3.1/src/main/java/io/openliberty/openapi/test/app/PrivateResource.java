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
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@SecurityRequirement(name = "oauth", scopes = "test")
@RolesAllowed("restricted")
@Tag(name = "private")
@Path("/private")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class PrivateResource {

    @Inject
    private RecordStore records;

    @Path("/{id}")
    @GET
    public ExampleRecord get(@Schema(description = "The record ID") @PathParam("id") int id) {
        return records.get(id);
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
