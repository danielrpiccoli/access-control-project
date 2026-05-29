package access_control.repository;

import access_control.entity.Scheduling;
import access_control.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long>{
    List<Scheduling> findByUser(AppUser user);
}
