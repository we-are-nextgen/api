package org.nextgen.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;

import org.nextgen.model.Journey;
import org.nextgen.service.JourneyService;

@Path("/journeys")
public class JourneyController {
    
    @Inject
    JourneyService journeyService;

    @GET
    @Path("/")
    public List<Journey> getJourneys() {
        return journeyService.getAllJourneys();
    }

    @GET
    @Path("/{id}")
    public Journey getJourneyById(@PathParam("id") long id) {
        return journeyService.getByJourneyId(id); 
    }

    @GET
    @Path("/name/{name}")
    public Journey getJourneyByName(@PathParam("name") String name) {
        return journeyService.findJourneyByName(name);
    }

}
