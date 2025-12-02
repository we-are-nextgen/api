package org.nextgen.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

import org.nextgen.model.ExerciseSubmission;
import org.nextgen.model.ITProfessional;
import org.nextgen.model.Lab;
import org.nextgen.model.UserLabProgress;

@ApplicationScoped
public class LabService {
    
    public Lab getLabById(Long id) {
        return Lab.findById(id);
    }
    
    public java.util.List<Lab> getAllLabs() {
        return Lab.listAll();
    }

    /**
     * 
     * @param id
     * @param userId    // user email
     * @return
     */
    public Map<Object,Object> getLabWithProgress(Long LabId, String userId){
        Map<Object,Object> map = new HashMap<Object,Object>();
        Lab lab = Lab.findById(LabId);
        map.put("lab", lab);
        ITProfessional user = ITProfessional.getUserByEmail(userId);
        if (user == null || lab == null)
            throw new IllegalStateException("User or Lab not found.");
        UserLabProgress progress = UserLabProgress.find("user.id = ?1 AND lab.id = ?2", user.id, LabId)
                .firstResult();
        map.put("progress", progress);
        map.put("exerciseSubmission",ExerciseSubmission.findByUserAndLab(user, lab));
        map.put("userId", user.id);
        return map;
    }

    
    
}
