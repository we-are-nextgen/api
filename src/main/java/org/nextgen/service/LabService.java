package org.nextgen.service;

import jakarta.enterprise.context.ApplicationScoped;

import org.nextgen.model.Lab;

@ApplicationScoped
public class LabService {
    
    public Lab getLabById(Long id) {
        return Lab.findById(id);
    }
    
    public java.util.List<Lab> getAllLabs() {
        return Lab.listAll();
    }

    
}
