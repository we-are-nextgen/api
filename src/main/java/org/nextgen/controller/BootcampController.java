package org.nextgen.controller;

import org.nextgen.model.UserBootcamp;

import java.util.List;
import java.util.Map;

import org.nextgen.dto.UserBootcampDTO;
import org.nextgen.model.Bootcamp;
import org.nextgen.model.BootcampStart;
import org.nextgen.service.BootcampService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@Path("/bootcamp")
public class BootcampController {
    
    @Inject
    BootcampService bootcampService;

    @GET
    @Path("")
    public List<Bootcamp> getall(){
        return bootcampService.getAllBootCamps(0, 10);
    }

    @GET
    @Path("/status/count")
    public Map<BootcampStart.STATUS, Long> getBootCampCountByStatus(){
        return bootcampService.getBootCampCountByStatus();
    }

    @GET
    @Path("/starting/{weeks}")
    public List<Bootcamp> getStartingWithinNextTwoMonths(
        @PathParam("weeks") Long weeks
    ){
        return bootcampService.findStartingWithinNextTwoMonths(weeks);
    }

    @GET
    @Path("/user")
    public List<UserBootcampDTO> getUserBootcamps(
        @QueryParam("email") String email
    ){
        return bootcampService.findBootcampsByUser(email);
    }

    @POST
    @Path("/{bootcampId}/status")
    public BootcampStart setBootCampStatus(
        @PathParam("bootcampId") Long bootcampStartId,
        @QueryParam("status") String status
    ){
        return bootcampService.setStatus(bootcampStartId,status);
    }



    @POST
    @Path("/{bootcampId}/enroll")
    public UserBootcamp enroll(
        @PathParam("bootcampId") Long bootcampId,
        @QueryParam("email") String email
    ) {
        return bootcampService.enrollUser(email, bootcampId);
    }

    @POST
    @Path("/{bootcampId}/start")
    public BootcampStart startBootcamp(
        @PathParam("bootcampId") Long bootcampId,
        @QueryParam("email") String email,
        @QueryParam("cohortName") String cohortName
    ){
        return bootcampService.startBootcamp(bootcampId, cohortName, email);
    }
    

}
