package access_control.repository;

import access_control.entity.Scheduling;
import access_control.entity.AppUser;
import access_control.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long>{
    List<Scheduling> findByUser(AppUser user);

    @Query("SELECT s FROM Scheduling s WHERE s.space = :space AND s.scheduledDate = :date "
            + "AND s.status <> 'CANCELLED' AND s.startTime < :endTime AND s.endTime > :startTime")
    List<Scheduling> findOverlapping(@Param("space") Space space, @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
}
