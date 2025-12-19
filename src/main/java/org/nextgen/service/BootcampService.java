package org.nextgen.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.nextgen.dto.BootcampEnrollmentCheckDTO;
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

    /**
     * get count of all bootcamps in the system
     * @return
     */
    public Long getBootCampCount(){
        return Bootcamp.count();
    }

    /**
     * get paginated list of bootcamps
     * @param page
     * @param pageSize
     * @return
     */
    public List<Bootcamp> getAllBootCamps(int page, int pageSize){
        return Bootcamp.findAllPaginated(page, pageSize);
    }

    /**
     * get list of bootcamps by status
     * @return
     */
    public Map<BootcampStart.STATUS, Long> getBootCampCountByStatus() {
        PanacheQuery<BootcampStart> query = BootcampStart.findAll();
        return query.stream()
                    .collect(Collectors.groupingBy(
                        b -> b.status,
                        Collectors.counting()
                    ));
    } 

    /**
     * get list of bootcamps starting in [weeks]
     * @param weeks
     * @return
     */
    public List<Bootcamp> getBootcampsStartingWithin(Long weeks) {
        LocalDate today = LocalDate.now();
        LocalDate twoMonthsLater = today.plusWeeks(weeks);

        return Bootcamp.find(
            "expectedStartDate BETWEEN ?1 AND ?2",
            today,
            twoMonthsLater
        ).list();
    }    

    /**
     * get user bootcamps
     * @param email
     * @return
     */
    public List<UserBootcampDTO> findBootcampsByUser(String email) {
        ITProfessional user = ITProfessional.getUserByEmail(email);
        return em.createQuery("""
             SELECT new org.nextgen.dto.UserBootcampDTO(
                bc.id,
                bc.name,
                bc.version,
                bs.status,
                bs.startedAt,
                bs.cohortName,
                bc.durationWeeks,
                bs.id
            )
            FROM UserBootcamp ub
            JOIN ub.bootcampStart bs
            JOIN bs.bootcamp bc
            WHERE ub.user = :user
            ORDER BY bs.startedAt DESC
        """, UserBootcampDTO.class)
        .setParameter("user", user)
        .getResultList();
    }

    /**
     * get Bootcamp by id
     * @param id
     * @return
     */
    public Bootcamp getBootcamp(Long id){
        return Bootcamp.findById(id);
    }


    public Optional<BootcampEnrollmentCheckDTO> isBootcampReadyForEnrollment(Long id, String email){
        List<BootcampEnrollmentCheckDTO> result = em.createQuery("""
            SELECT new org.nextgen.dto.BootcampEnrollmentCheckDTO(
                b.id,
                bs.id,
                bs.status,
                b.capacity,
                COUNT(ub.id)
            )
            FROM BootcampStart bs
            JOIN bs.bootcamp b
            LEFT JOIN UserBootcamp ub ON ub.bootcampStart = bs
            WHERE b.id = :bootcampId
              AND bs.status = :status
            GROUP BY b.id, bs.id, b.capacity, bs.status
        """, BootcampEnrollmentCheckDTO.class)
        .setParameter("bootcampId", id)
        .setParameter("status", BootcampStart.STATUS.OPEN_FOR_ENROLLMENT)
        .getResultList();
        
        // check if user already enrolled 

        Optional<BootcampEnrollmentCheckDTO> bootcampEnrollmentCheckDTO = result.stream().findFirst();
        if(!bootcampEnrollmentCheckDTO.isEmpty()) {
            bootcampEnrollmentCheckDTO.ifPresent(dto -> 
                {
                    ITProfessional user = ITProfessional.getUserByEmail(email);
                    BootcampStart bootcampStart = BootcampStart.find("bootcamp.id=?1",dto.bootcampStartId).firstResult();
                    UserBootcamp userBootcamp = UserBootcamp.findByUserAndBootcampStart(bootcampStart, user);
                    dto.amIEnrolled=(userBootcamp != null);
                }
            );
        }
        return bootcampEnrollmentCheckDTO;
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
        UserBootcamp userBootcamp = UserBootcamp.findByUserAndBootcampStart(bootcampStart, user);
        if (userBootcamp!=null)
            return userBootcamp;
        userBootcamp = new UserBootcamp(user,bootcampStart);;
        userBootcamp.persist();
        return userBootcamp;
    }
    
}
