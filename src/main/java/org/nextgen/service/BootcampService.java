package org.nextgen.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.nextgen.dto.BootcampEnrollmentCheckDTO;
import org.nextgen.dto.UserBootcampDTO;
import org.nextgen.model.Bootcamp;
import org.nextgen.model.BootcampProgress;
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
                bs.id,
                ub.id
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
    public Bootcamp getBootcamp(UUID id){
        return Bootcamp.findById(id);
    }


    public Optional<BootcampEnrollmentCheckDTO> isBootcampReadyForEnrollment(UUID id, String email){
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
                    BootcampStart bootcampStart = BootcampStart.findById(dto.bootcampStartId);
                    UserBootcamp userBootcamp = UserBootcamp.findByUserAndBootcampStart(bootcampStart, user);
                    dto.amIEnrolled=(userBootcamp != null);
                    dto.userBootcampId = (userBootcamp != null)?userBootcamp.id:null;
                }
            );
        }
        return bootcampEnrollmentCheckDTO;
    }

     /**
     * get User Bootcamp by Id
     * @param userBootcampId
     * @return
     */
    public org.nextgen.dto.bootcamp.UserBootcampDTO getUserBootcamp(UUID userBootcampId){
        UserBootcamp userBootcamp = UserBootcamp.find("""
            select ub
            from UserBootcamp ub
            join fetch ub.bootcampStart bs
            join fetch bs.bootcamp
            join fetch bs.userBootcamps ub2
            join fetch ub2.user
            where ub.id = ?1
        """, userBootcampId).singleResult();
        BootcampProgress progress = BootcampProgress.find("bootcampStart.id", userBootcamp.bootcampStart.id).firstResult();
        return org.nextgen.dto.bootcamp.UserBootcampDTO.tDto(userBootcamp,progress);
    }

    public BootcampProgress getBootcampProgress(UUID bootcampStartId){
        return BootcampProgress.find("bootcampStart.id", bootcampStartId).firstResult();
    }

    // ==========================
    // Transactional 
    // ==========================

    @Transactional
    public BootcampStart startBootcamp(UUID bootcampId, String cohortName, String email){
        return BootcampStart.start(email, bootcampId, cohortName);
    }

    @Transactional
    public BootcampStart setStatus(UUID bootcampStartId, String satus){
        BootcampStart bootcampStart = BootcampStart.findById(bootcampStartId);
        bootcampStart.status = BootcampStart.STATUS.valueOf(satus);
        bootcampStart.persist();
        return bootcampStart;
    }

    @Transactional
    public UserBootcamp enrollUser(String email,UUID bootcampStartId){
        ITProfessional user = ITProfessional.getUserByEmail(email);
        BootcampStart bootcampStart = BootcampStart.findById(bootcampStartId);
        UserBootcamp userBootcamp = UserBootcamp.findByUserAndBootcampStart(bootcampStart, user);
        if (userBootcamp!=null)
            return userBootcamp;
        userBootcamp = new UserBootcamp(user,bootcampStart);;
        userBootcamp.persist();
        return userBootcamp;
    }

    @Transactional
    public BootcampStart bootcampProgress(UUID bootcampStartId){
        BootcampProgress progress = BootcampProgress.find("bootcampStart.id", bootcampStartId).firstResult();
        if (progress == null){
            BootcampProgress newProgress = new BootcampProgress();
            BootcampStart bootcampStart = BootcampStart.findById(bootcampStartId);
            newProgress.bootcampStart = bootcampStart;
            newProgress.persist();
            bootcampStart.bootcamp.layers.forEach(layer -> {
                newProgress.initializeLayer(layer.id);
            });
            
        } else {
            // update existing progress logic if needed
        }
        return BootcampStart.findById(bootcampStartId);
    }
   
    
}
