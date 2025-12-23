package org.nextgen.controller;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

import org.nextgen.model.Domain;
import org.nextgen.service.DomainService;

@Path("/domains")
public class DomainController {
    
    @Inject
    DomainService domainService;

    @GET
    @Path("/")
    public List<Domain> getTracks() {
        return domainService.getAllDomains();
    }    

    @GET
    @Path("/{id}")
    public Domain get(@PathParam("id") UUID id) {
        return domainService.getDomainById(id);
    }

    @GET
    @Path("/name/{name}")
    public Domain get(@PathParam("name") String name) {
        return domainService.findDomainByName(name);
    }
}
