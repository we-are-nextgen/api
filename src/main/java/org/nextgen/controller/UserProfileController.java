package org.nextgen.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.inject.Inject;

import java.util.List;
import org.nextgen.model.ITProfessional;
import org.nextgen.service.UserProfileService;

@Path("/profile")
public class UserProfileController {
     @Inject
    UserProfileService userProfileService;

    @GET
    @Path("/")
    public List<ITProfessional> getAll() {
        return userProfileService.getAll();
    }    

    @GET
    @Path("/{id}")
    public ITProfessional get(@PathParam("id") long id) {
        return userProfileService.getById(id);
    }

    @GET
    @Path("/userid/{userid}")
    public ITProfessional get(@PathParam("userid") String userid) {
        return userProfileService.getByUserId(userid);
    }
}
