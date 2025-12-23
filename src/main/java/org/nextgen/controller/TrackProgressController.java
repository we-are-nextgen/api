package org.nextgen.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

import org.nextgen.dto.TrackProgressDTO;
import org.nextgen.model.UserLabProgress;
import org.nextgen.service.TrackProgressService;

@Path("/progress")
@Produces(MediaType.APPLICATION_JSON)
public class TrackProgressController {

    @Inject
    TrackProgressService progressService;

    @GET
    @Path("/{trackId}/user/{userId}")
    public TrackProgressDTO getProgress(
        @PathParam("trackId") UUID trackId,
        @PathParam("userId") UUID userId) 
    {
        return progressService.getProgress(userId, trackId);
    }

    @GET
    @Path("/email")
    public TrackProgressDTO getProgressByEmail(
            @QueryParam("email") String email,
            @QueryParam("trackId") UUID trackId) {
        return progressService.getProgressByEmail(email, trackId);
    }


    @POST
    @Path("/{trackId}/enroll")
    public UserLabProgress enroll(
        @PathParam("trackId") UUID trackId,
        @QueryParam("email") String email
    ) {
        return progressService.enroll(email, trackId);
    }

    @POST
    @Path("/lab/complete/{labId}")
    public Response markCompleted(
            @PathParam("labId") UUID labId,
            @QueryParam("trackId") UUID trackId,
            @QueryParam("email") String email
    ) {
        var updated = progressService.markLabCompleted(email, labId, trackId);
        return Response.ok(updated).build();
    }
}
