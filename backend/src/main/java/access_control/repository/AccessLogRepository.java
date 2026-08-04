package access_control.repository;

import access_control.entity.AccessLog;
import access_control.entity.AppUser;
import access_control.entity.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long>{
    AccessLog findByScheduling(Scheduling scheduling);
    List<AccessLog> findByScheduling_User(AppUser user);
}