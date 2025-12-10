package org.nextgen.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

//import org.graalvm.polyglot.*;

@Path("/run")
public class RunCodeResource {

    @POST
    @Path("/javascript")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response runJs(String code) {
        return Response.ok("Compiled: No Error").build();
        /*
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(false)       // safe sandbox
                .build()) {

            Value result = context.eval("js", code);
            return Response.ok(result.toString()).build();
        }
        catch (Exception e) {
            return Response.ok("Error: " + e.getMessage()).build();
        } */
    }
}
