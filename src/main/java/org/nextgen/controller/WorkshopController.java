package org.nextgen.controller;

import java.util.List;

import org.nextgen.dto.WorkshopCatalogEntryDTO;
import org.nextgen.dto.WorkshopSessionRequestDTO;
import org.nextgen.dto.WorkshopSessionResponseDTO;
import org.nextgen.service.EducatesWorkshopService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/workshops")
@Produces(MediaType.APPLICATION_JSON)
public class WorkshopController {

    @Inject
    EducatesWorkshopService educatesWorkshopService;

    @GET
    public List<WorkshopCatalogEntryDTO> listWorkshops() {
        return educatesWorkshopService.listWorkshops();
    }

    @POST
    @Path("/sessions")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSession(
            @QueryParam("email") String email,
            WorkshopSessionRequestDTO request
    ) {
        if (email == null || email.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("email query parameter is required")
                    .build();
        }
        if (request == null || request.workshopName == null || request.workshopName.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("workshopName is required")
                    .build();
        }

        try {
            WorkshopSessionResponseDTO session = educatesWorkshopService.requestSession(
                    request.workshopName,
                    email,
                    request.returnPath,
                    request.firstName,
                    request.lastName
            );
            return Response.ok(session).build();
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("no capacity")) {
                return Response.status(503).entity(e.getMessage()).build();
            }
            return Response.status(Response.Status.BAD_GATEWAY).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/sessions/active")
    public List<WorkshopSessionResponseDTO> listActiveSessions(@QueryParam("email") String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email query parameter is required");
        }
        return educatesWorkshopService.listActiveSessions(email);
    }
}
