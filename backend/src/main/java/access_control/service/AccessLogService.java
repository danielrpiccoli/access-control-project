package access_control.service;

import access_control.dto.*;
import access_control.entity.*;
import access_control.mapper.SchedulingMapper;
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

    @Autowired
    private SchedulingMapper schedulingMapper;

    public AccessLogResponseDTO createAccessLog(AccessLogRequestDTO dto) {
        Scheduling scheduling = schedulingRepository.findById(dto.getSchedulingId()).orElseThrow();

        AccessLog accessLog = new AccessLog();
        accessLog.setScheduling(scheduling);

        AccessLog saved = accessLogRepository.save(accessLog);

        AccessLogResponseDTO response = new AccessLogResponseDTO();
        response.setId(saved.getId());
        response.setEntryTimestamp(LocalDateTime.now());
        response.setSuccess(true);
        response.setScheduling(schedulingMapper.toRespoonseDTO(saved.getScheduling()));

        return response;
    }
}
