package org.nextgen.service;

import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;


import org.nextgen.model.ITProfessional;

@ApplicationScoped
public class UserProfileService {
    
    public List<ITProfessional> getAll() {
        return ITProfessional.listAll();
    }

    public ITProfessional getById(UUID id) {
        return ITProfessional.findById(id);
    }

    public ITProfessional getByUserId(String userId) {
        return ITProfessional.find("userId", userId).firstResult();
    }
    
}
