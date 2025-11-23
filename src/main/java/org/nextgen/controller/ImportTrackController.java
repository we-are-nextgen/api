package org.nextgen.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.nextgen.dto.ImportTrackDTO;
import org.nextgen.model.LearningTrack;
import org.nextgen.service.ImportTrackService;

@Path("/import")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImportTrackController {

    @Inject
    ImportTrackService importTrackService;

    @POST
    @Path("/track")
    public LearningTrack importTrack(ImportTrackDTO importTrackDTO) {
        try {
            return importTrackService.importTrack(importTrackDTO);    
        } catch (Exception e) {
            // handle exception
            return null;
        }
        
    }
}
