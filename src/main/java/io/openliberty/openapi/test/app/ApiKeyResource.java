package io.openliberty.openapi.test.app;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
