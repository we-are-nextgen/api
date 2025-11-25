package org.nextgen.service;

import java.util.List;
import java.util.HashMap;
import jakarta.enterprise.context.ApplicationScoped;

import org.nextgen.model.LearningTrack;


@ApplicationScoped
public class LearningTrackService {

    public List<LearningTrack> getAllLearningTracks() {
        return LearningTrack.listAll();
    }

    public List<LearningTrack> getLearningTracksByDomainName(String domainName) {
        return LearningTrack.findByDomainName(domainName);
    }

    public List<LearningTrack> getLearningTracksByDomainId(Long domainId) {
        return LearningTrack.findByDomainId(domainId);
    }

    public  HashMap<Object,Object> getLearningTracksByDomainPaged(Long domainId, int page, int pageSize) {
        HashMap<Object,Object> result = new HashMap<Object,Object> ();
        result.put("count", LearningTrack.count());
        result.put("tracks",  LearningTrack.findByDomainPaginated(domainId,page,pageSize));
        return result;
    }
    
    public LearningTrack getLearningTrackById(Long id) {
        return LearningTrack.findById(id);
    }

    

    public LearningTrack updateLearningTrack(Long id, String name, String description, String icon) {
        LearningTrack learningTrack = LearningTrack.findById(id);
        if (learningTrack != null) {
            learningTrack.name = name;
            learningTrack.description = description;
            //...
            learningTrack.persist();
        }
        return learningTrack;
    }

    public LearningTrack deleteLearningTrack(Long id) {
        LearningTrack learningTrack = LearningTrack.findById(id);
        if (learningTrack != null) {
            learningTrack.delete();
        }
        return learningTrack;
    }

    public LearningTrack findLearningTrackByName(String name) {
        return LearningTrack.find("name", name).firstResult();
    }   

    public Long getCount() {
        return LearningTrack.count();
    }

    public LearningTrack createLearningTrack(String name, String description, String icon) {
        LearningTrack learningTrack = new LearningTrack();
        learningTrack.name = name;
        learningTrack.description = description;
        learningTrack.icon = icon;
        learningTrack.persist();
        return learningTrack;
    }

    

    
}

