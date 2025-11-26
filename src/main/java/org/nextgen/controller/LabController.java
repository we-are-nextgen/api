package org.nextgen.controller;

import java.util.List;

import org.nextgen.model.Lab;
import org.nextgen.service.LabService;


import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;


@Path("/labs")
public class LabController {
       
    @Inject
    LabService labService;

     @GET
    @Path("/")
    public List<Lab> getTracks() {
        return labService.getAllLabs();
    }    

    @GET
    @Path("/{id}")
    public Lab get(@PathParam("id") long id) {
        return labService.getLabById(id);
    }


}
