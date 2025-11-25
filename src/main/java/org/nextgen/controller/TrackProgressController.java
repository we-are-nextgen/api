package org.nextgen.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.nextgen.dto.TrackProgressDTO;
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
        System.out.println("Received email: " + email + ", trackId: " + trackId);
        return progressService.getProgressByEmail(email, trackId);
    }
}
