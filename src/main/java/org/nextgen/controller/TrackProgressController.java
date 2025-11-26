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
        @PathParam("trackId") Long trackId,
        @PathParam("userId") Long userId) 
    {
        return progressService.getProgress(userId, trackId);
    }

    @GET
    @Path("/email")
    public TrackProgressDTO getProgressByEmail(
            @QueryParam("email") String email,
            @QueryParam("trackId") Long trackId) {
        return progressService.getProgressByEmail(email, trackId);
    }


    @POST
    @Path("/{trackId}/enroll")
    public UserLabProgress enroll(
        @PathParam("trackId") Long trackId,
        @QueryParam("email") String email
    ) {
        return progressService.enroll(email, trackId);
    }

    @POST
    @Path("/lab/complete/{labId}")
    public Response markCompleted(
            @PathParam("labId") long labId,
            @QueryParam("email") String email
    ) {
        System.out.println("Marking lab " + labId + " as completed for user " + email);
        var updated = progressService.markLabCompleted(email, labId);
        return Response.ok(updated).build();
    }
}
