package org.nextgen.service;


import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;

import org.nextgen.model.Domain;

@ApplicationScoped
public class DomainService {

    public List<Domain> getAllDomains() {
        return Domain.listAll();
    }

    public Domain getDomainById(Long id) {
        return Domain.findById(id);
    }

    public Domain updateDomain(Long id, String name, String description, String community) {
        Domain domain = Domain.findById(id);
        if (domain != null) {
            domain.name = name;
            domain.description = description;
            domain.community = community;
            domain.persist();
        }
        return domain;
    }

    /* 
    public Long deleteDomain(Long id) {
        boolean deleted = Domain.deleteById(id);
        return deleted ? id : null;
    }*/

    public Domain deleteDomainEntity(Long id) {
        Domain domain = Domain.findById(id);
        if (domain != null) {
            domain.delete();
        }
        return domain;
    }

    public Domain findDomainByName(String name) {
        return Domain.find("name", name).firstResult();
    }   

    public Long getCount() {
        return Domain.count();
    }

    public Domain createDomain(String name, String description, String community) {
        Domain domain = new Domain();
        domain.name = name;
        domain.description = description;
        domain.community = community;
        domain.persist();
        return domain;
    }
}
