package org.nextgen.service;

import java.util.List;
import java.util.UUID;
import java.util.HashMap;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.nextgen.model.LearningTrack;


@ApplicationScoped
public class LearningTrackService {

    public List<LearningTrack> getAllLearningTracks() {
        return LearningTrack.listAll();
    }

    public List<LearningTrack> getLearningTracksByDomainName(String domainName) {
        return LearningTrack.findByDomainName(domainName);
    }

    public List<LearningTrack> getLearningTracksByDomainId(UUID domainId) {
        return LearningTrack.findByDomainId(domainId);
    }

    public  HashMap<Object,Object> getLearningTracksByDomainPaged(UUID domainId, int page, int pageSize) {
        HashMap<Object,Object> result = new HashMap<Object,Object> ();
        result.put("count", LearningTrack.count());
        result.put("tracks",  LearningTrack.findByDomainPaginated(domainId,page,pageSize));
        return result;
    }
    
    public LearningTrack getLearningTrackById(UUID id) {
        return LearningTrack.findById(id);
    }

    
    @Transactional
    public LearningTrack updateLearningTrack(UUID id, String name, String description, String icon) {
        LearningTrack learningTrack = LearningTrack.findById(id);
        if (learningTrack != null) {
            learningTrack.name = name;
            learningTrack.description = description;
            //...
            learningTrack.persist();
        }
        return learningTrack;
    }

    @Transactional
    public LearningTrack deleteLearningTrack(UUID id) {
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
    
    @Transactional
    public LearningTrack createLearningTrack(String name, String description, String icon) {
        LearningTrack learningTrack = new LearningTrack();
        learningTrack.name = name;
        learningTrack.description = description;
        learningTrack.icon = icon;
        learningTrack.persist();
        return learningTrack;
    }

    

    
}

