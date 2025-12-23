package org.nextgen.controller;

import org.nextgen.model.UserBootcamp;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.nextgen.dto.BootcampEnrollmentCheckDTO;
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

/**
 * Bootamp supported methods
 * @Path("")                   getall : get all bootcamps
 * @Path("{id}")               getBootCamp: get bootcamp by id
 * @Path("/status/count")      getBootCampCountByStatus: List of <status:count>
 * @Path("/starting/{weeks}")  getBootcampsStartingWithin : starting within weeks
 */
@Path("/bootcamp")
public class BootcampController {
    
    @Inject
    BootcampService bootcampService;


    // ========================
    //  bootcamp functions.   |
    // ========================
    @GET
    @Path("")
    public List<Bootcamp> getall(){
        return bootcampService.getAllBootCamps(0, 10);
    }

    @GET
    @Path("{id}")
    public Bootcamp getBootCamp(
        @PathParam("id") UUID id 
    ){
        return bootcampService.getBootcamp(id);
    }

    @GET
    @Path("/status/count")
    public Map<BootcampStart.STATUS, Long> getBootCampCountByStatus(){
        return bootcampService.getBootCampCountByStatus();
    }

    @GET
    @Path("/starting/{weeks}")
    public List<Bootcamp> getBootcampsStartingWithin(
        @PathParam("weeks") Long weeks
    ){
        return bootcampService.getBootcampsStartingWithin(weeks);
    }

    @GET
    @Path("{id}/ready")
    public Optional<BootcampEnrollmentCheckDTO> isBootcampReadyForEnrollment(
        @PathParam("id") UUID id,
        @QueryParam("email") String email
    ){
         return bootcampService.isBootcampReadyForEnrollment(id,email);
    }


    @POST
    @Path("/{bootcampId}/status")
    public BootcampStart setBootCampStatus(
        @PathParam("bootcampId") UUID bootcampStartId,
        @QueryParam("status") String status
    ){
        return bootcampService.setStatus(bootcampStartId,status);
    }

    @POST
    @Path("/{bootcampId}/start")
    public BootcampStart startBootcamp(
        @PathParam("bootcampId") UUID bootcampId,
        @QueryParam("email") String email,
        @QueryParam("cohortName") String cohortName
    ){
        return bootcampService.startBootcamp(bootcampId, cohortName, email);
    }
    
    // ============================
    // user bootcamp functions.   |
    // ============================

    @GET
    @Path("/user")
    public List<UserBootcampDTO> getUserBootcamps(
        @QueryParam("email") String email
    ){
        return bootcampService.findBootcampsByUser(email);
    }

    @POST
    @Path("/{bootcampId}/enroll")
    public UserBootcamp enroll(
        @PathParam("bootcampId") UUID bootcampId,
        @QueryParam("email") String email
    ) {
        return bootcampService.enrollUser(email, bootcampId);
    }    

    @GET
    @Path("/user/{userBootcampId}")
    public org.nextgen.dto.bootcamp.UserBootcampDTO getUserBootcamp(
            @PathParam("userBootcampId") UUID userBootcampId
    ){
        System.out.println(userBootcampId);
        return bootcampService.getUserBootcamp(userBootcampId);
        
    }
    
}
