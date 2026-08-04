package access_control.service;

import access_control.dto.*;
import access_control.entity.*;
import access_control.mapper.SchedulingMapper;
import access_control.repository.AccessLogRepository;
import access_control.repository.AppUserRepository;
import access_control.repository.SchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class AccessLogService {
    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private SchedulingMapper schedulingMapper;

    public AccessLogResponseDTO createAccessLog(AccessLogRequestDTO dto, String email) {
        Scheduling scheduling = schedulingRepository.findById(dto.getSchedulingId()).orElseThrow();
        AppUser requester = appUserRepository.findByEmail(email);

        if (!scheduling.getUser().getId().equals(requester.getId())) {
            throw new AccessDeniedException("This scheduling does not belong to you");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = LocalDateTime.of(scheduling.getScheduledDate(), scheduling.getStartTime());
        LocalDateTime windowEnd = LocalDateTime.of(scheduling.getScheduledDate(), scheduling.getEndTime());
        boolean withinWindow = !now.isBefore(windowStart) && !now.isAfter(windowEnd);

        AccessLog accessLog = new AccessLog();
        accessLog.setScheduling(scheduling);
        accessLog.setEntryTimestamp(now);
        accessLog.setSuccess(withinWindow);

        AccessLog saved = accessLogRepository.save(accessLog);

        AccessLogResponseDTO response = new AccessLogResponseDTO();
        response.setId(saved.getId());
        response.setEntryTimestamp(saved.getEntryTimestamp());
        response.setSuccess(saved.isSuccess());
        response.setScheduling(schedulingMapper.toResponseDTO(saved.getScheduling()));

        return response;
    }
    
    public List<AccessLogResponseDTO> getAllAccessLogs(String email) {
        AppUser requester = appUserRepository.findByEmail(email);
        List<AccessLog> accessLogs = "ADMIN".equals(requester.getRole())
                ? accessLogRepository.findAll()
                : accessLogRepository.findByScheduling_User(requester);
        List<AccessLogResponseDTO> response = new ArrayList<>();
        for (AccessLog accessLog : accessLogs) {
            AccessLogResponseDTO dto = new AccessLogResponseDTO();
            dto.setId(accessLog.getId());
            dto.setEntryTimestamp(accessLog.getEntryTimestamp());
            dto.setSuccess(accessLog.isSuccess());
            dto.setScheduling(schedulingMapper.toResponseDTO(accessLog.getScheduling()));
            response.add(dto);
        }
        return response;
    }
    
    public AccessLogResponseDTO getAccessLogById(Long id) {
        AccessLog accessLog = accessLogRepository.findById(id).orElseThrow();
        AccessLogResponseDTO response = new AccessLogResponseDTO();
        response.setId(accessLog.getId());
        response.setEntryTimestamp(accessLog.getEntryTimestamp());
        response.setSuccess(accessLog.isSuccess());
        response.setScheduling(schedulingMapper.toResponseDTO(accessLog.getScheduling()));

        return response;
    }
}
