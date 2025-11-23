package org.nextgen.controller;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;


import org.nextgen.model.LearningTrack;
import org.nextgen.service.LearningTrackService;

@Path("/tracks")
public class LearningTrackController {
    @Inject
    LearningTrackService learningTrackService;

    @GET
    @Path("/")
    public List<LearningTrack> getTracks() {
        return learningTrackService.getAllLearningTracks();
    }    

    @GET
    @Path("/{id}")
    public LearningTrack get(@PathParam("id") long id) {
        return learningTrackService.getLearningTrackById(id);
    }

    @GET
    @Path("/name/{name}")
    public LearningTrack get(@PathParam("name") String name) {
        return learningTrackService.findLearningTrackByName(name);
    }

    @GET
    @Path("/domain/{id}")
    public List<LearningTrack> getByDomainId(@PathParam("id") long id) {
        return learningTrackService.getLearningTracksByDomainId(id);
    }

    @GET
    @Path("/domain/{id}/paginated")
    public HashMap<Object,Object> getByDomainIdPaged(
            @PathParam("id") long domainId,
            @QueryParam("page") int page,
            @QueryParam("pageSize") int pageSize
    ) {
        return learningTrackService.getLearningTracksByDomainPaged(domainId, page, pageSize);
    }

}
