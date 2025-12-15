package org.nextgen.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.nextgen.dto.UserBootcampDTO;
import org.nextgen.model.Bootcamp;
import org.nextgen.model.BootcampStart;
import org.nextgen.model.ITProfessional;
import org.nextgen.model.UserBootcamp;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BootcampService {
    @PersistenceContext
    EntityManager em;

    public Long getBootCampCount(){
        return Bootcamp.count();
    }

    public List<Bootcamp> getAllBootCamps(int page, int pageSize){
        return Bootcamp.findAllPaginated(page, pageSize);
    }

    public Map<BootcampStart.STATUS, Long> getBootCampCountByStatus() {
        PanacheQuery<BootcampStart> query = BootcampStart.findAll();
        return query.stream()
                    .collect(Collectors.groupingBy(
                        b -> b.status,
                        Collectors.counting()
                    ));
    } 

    public List<Bootcamp> findStartingWithinNextTwoMonths(Long weeks) {
        LocalDate today = LocalDate.now();
        LocalDate twoMonthsLater = today.plusWeeks(weeks);

        return Bootcamp.find(
            "expectedStartDate BETWEEN ?1 AND ?2",
            today,
            twoMonthsLater
        ).list();
    }    

    public List<UserBootcampDTO> findBootcampsByUser(String email) {
        ITProfessional user = ITProfessional.getUserByEmail(email);
        return em.createQuery("""
             SELECT new org.nextgen.dto.UserBootcampDTO(
                bc.id,
                bc.name,
                bc.version,
                ubs.status,
                ubs.startedAt,
                ubs.cohortName
            )
            FROM UserBootcamp ub
            JOIN ub.bootcampStart ubs
            JOIN ubs.bootcamp bc
            WHERE ub.user = :user
            ORDER BY ubs.startedAt DESC
        """, UserBootcampDTO.class)
        .setParameter("user", user)
        .getResultList();
    }

    public List<UserBootcamp> getBootCampByUser(String email) {
        ITProfessional user = ITProfessional.getUserByEmail(email);
        return UserBootcamp.find("""
        SELECT ub
        FROM UserBootcamp ub
        JOIN FETCH ub.bootcampStart ubs
        JOIN FETCH ubs.bootcamp
        WHERE ub.user = ?1
        """, user).list();
        //return UserBootcamp.find("user=?1", user).list();
    } 

    @Transactional
    public BootcampStart startBootcamp(Long bootcampId, String cohortName, String email){
        return BootcampStart.start(email, bootcampId, cohortName);
    }

    @Transactional
    public BootcampStart setStatus(Long bootcampStartId, String satus){
        BootcampStart bootcampStart = BootcampStart.findById(bootcampStartId);
        bootcampStart.status = BootcampStart.STATUS.valueOf(satus);
        bootcampStart.persist();
        return bootcampStart;
    }

    @Transactional
    public UserBootcamp enrollUser(String email,Long bootcampStartId){
        ITProfessional user = ITProfessional.getUserByEmail(email);
        BootcampStart bootcampStart = BootcampStart.findById(bootcampStartId);
        UserBootcamp userBootcamp = new UserBootcamp(user,bootcampStart);;
        userBootcamp.persist();
        return userBootcamp;
    }
    
}
