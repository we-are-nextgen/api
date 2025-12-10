package org.nextgen.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import org.nextgen.dto.importer.ImportParamDTO;
import org.nextgen.model.Bootcamp;
import org.nextgen.model.LearningTrack;
import org.nextgen.service.ImportBootcampService;
import org.nextgen.service.ImportTrackService;

@Path("/import")
public class ImportController {

    @Inject
    ImportTrackService importTrackService;

    @Inject
    ImportBootcampService importBootcampService;

    @POST
    @Path("/track")
    public LearningTrack importTrack(ImportParamDTO importTrackDTO) {
        try {
            return importTrackService.importTrack(importTrackDTO);    
        } catch (Exception e) {
            // handle exception
            return null;
        }
        
    }


    @GET
    @Path("/track/{lab}")
    public LearningTrack importTrack(@PathParam("lab") String lab) {
        try {
            ImportParamDTO importParamDTO = new ImportParamDTO();
            importParamDTO.gitRepoUrl = "https://github.com/we-are-nextgen/tracks";
            importParamDTO.gitPath = lab;
            importParamDTO.gitBranch = "main";
            return importTrackService.importTrack(importParamDTO);    
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
            return null;
        }
        
    }

    @GET
    @Path("/bootcamp/{bootcamp}")
    public Bootcamp importBootcamp(@PathParam("bootcamp") String lab) {
        try {
            ImportParamDTO importParamDTO = new ImportParamDTO();
            importParamDTO.gitRepoUrl = "https://github.com/we-are-nextgen/bootcamps";
            importParamDTO.gitPath = lab;
            importParamDTO.gitBranch = "main";
            return importBootcampService.importTrack(importParamDTO);    
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
            return null;
        }
        
    }


   
}
