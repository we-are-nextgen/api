package org.nextgen.service;

import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import org.nextgen.model.Journey;


@ApplicationScoped
public class JourneyService {
    @Inject
    EntityManager em; 

    public List<Journey> getJourneys() {
        return em.createNamedQuery("Journey.findAll", Journey.class)
                .getResultList();
    }   

    public List<Journey> getAllJourneys() {
        return Journey.listAll();
    }

    public Journey getByJourneyId(UUID id) {
        return Journey.findById(id);
    }

    public Journey updateJourney(UUID id, String name, String description) {
        Journey journey = Journey.findById(id);
        if (journey != null) {
            journey.name = name;
            journey.description = description;
            journey.persist();
        }
        return journey;
    }

    public Journey deleteJourneyEntity(UUID id) {
        Journey journey = Journey.findById(id);
        if (journey != null) {
            journey.delete();
        }
        return journey;
    }

    public Journey findJourneyByName(String name) {
        return Journey.find("name", name).firstResult();
    }   

    public Long getCount() {
        return Journey.count();
    }

    public Journey createJourney(String name, String description) {
        Journey journey = new Journey();
        journey.name = name;
        journey.description = description;
        journey.persist();
        return journey;
    }
}
