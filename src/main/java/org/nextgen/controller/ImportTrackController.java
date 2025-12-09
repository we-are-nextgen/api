package org.nextgen.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import org.nextgen.dto.ImportTrackDTO;
import org.nextgen.model.LearningTrack;
import org.nextgen.service.ImportTrackService;

@Path("/import")
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


    @GET
    @Path("/load/{lab}")
    public LearningTrack importTrack(@PathParam("lab") String lab) {
        try {
            ImportTrackDTO importTrackDTO = new ImportTrackDTO();
            importTrackDTO.gitRepoUrl = "https://github.com/we-are-nextgen/tracks";
            importTrackDTO.gitPath = lab;
            importTrackDTO.gitBranch = "main";
            return importTrackService.importTrack(importTrackDTO);    
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
            return null;
        }
        
    }
}
