package access_control.service;

import access_control.dto.*;
import access_control.entity.*;
import access_control.repository.AccessLogRepository;
import access_control.repository.SchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AccessLogService {
    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private SchedulingRepository schedulingRepository;

    public AccessLogResponseDTO createAccessLog(AccessLogRequestDTO dto) {
        Scheduling scheduling = schedulingRepository.findById(dto.getSchedulingId()).orElseThrow();

        AccessLog accessLog = new AccessLog();
        accessLog.setScheduling(scheduling);

        AccessLog saved = accessLogRepository.save(accessLog);

        AccessLogResponseDTO response = new AccessLogResponseDTO();
        response.setId(saved.getId());
        response.setEntryTimestamp(LocalDateTime.now());
        response.setSuccess(true);
        response.setScheduling(toSchedulingRespoonse(saved.getScheduling()));

        return response;
    }

    private SchedulingResponseDTO toSchedulingRespoonse(Scheduling scheduling) {
        SchedulingResponseDTO response = new SchedulingResponseDTO();
        response.setId(scheduling.getId());
        response.setScheduledDate(scheduling.getScheduledDate());
        response.setStartTime(scheduling.getStartTime());
        response.setEndTime(scheduling.getEndTime());
        response.setStatus(scheduling.getStatus());
        response.setUser(toUserResponse(scheduling.getUser()));
        response.setSpace(toSpaceResponse(scheduling.getSpace()));

        return response;
    }

    private AppUserResponseDTO toUserResponse(AppUser user) {
        AppUserResponseDTO response = new AppUserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }

    private SpaceResponseDTO toSpaceResponse(Space space) {
        SpaceResponseDTO response = new SpaceResponseDTO();
        response.setId(space.getId());
        response.setName(space.getName());
        response.setCapacity(space.getCapacity());
        response.setDescription(space.getDescription());
        response.setStatus(space.getStatus());
        return response;
    }
}
