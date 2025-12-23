package org.nextgen.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.nextgen.dto.LabSubmissionDTO;
import org.nextgen.model.Lab;
import org.nextgen.service.AutoGradingService;
import org.nextgen.service.LabService;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/labs")
public class LabController {
       
    @Inject
    LabService labService;
    @Inject
    AutoGradingService gradingService;

     @GET
    @Path("/")
    public List<Lab> getTracks() {
        return labService.getAllLabs();
    }    

    @GET
    @Path("/{id}")
    public Lab get(@PathParam("id") UUID id) {
        return labService.getLabById(id);
    }

    @GET
    @Path("/progress/{id}")
    public Response getWithProgress(
            @PathParam("id") UUID id,
            @QueryParam("email") String email
    ) {
        return Response.ok(
                labService.getLabWithProgress(id,email)
            ).build();
    }


    @POST
    @Path("/grade")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response gradeLab(LabSubmissionDTO dto) {
        Map<Object, Object> result = gradingService.grade(dto);
        return Response.ok(result).build(); 
    }

}
