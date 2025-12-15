package org.nextgen.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.nextgen.model.Bootcamp;
import org.nextgen.model.BootcampStart;
import org.nextgen.model.ITProfessional;
import org.nextgen.model.UserBootcamp;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BootcampService {
    
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
