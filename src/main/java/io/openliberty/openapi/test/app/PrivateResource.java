package io.openliberty.openapi.test.app;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
