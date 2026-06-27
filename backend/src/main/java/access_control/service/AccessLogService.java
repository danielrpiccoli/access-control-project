package access_control.service;

import access_control.dto.*;
import access_control.entity.*;
import access_control.mapper.SchedulingMapper;
import access_control.repository.AccessLogRepository;
import access_control.repository.SchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SchedulingMapper schedulingMapper;

    public AccessLogResponseDTO createAccessLog(AccessLogRequestDTO dto) {
        Scheduling scheduling = schedulingRepository.findById(dto.getSchedulingId()).orElseThrow();

        AccessLog accessLog = new AccessLog();
        accessLog.setScheduling(scheduling);
        accessLog.setEntryTimestamp(LocalDateTime.now());
        accessLog.setSuccess(dto.isSuccess());

        AccessLog saved = accessLogRepository.save(accessLog);

        AccessLogResponseDTO response = new AccessLogResponseDTO();
        response.setId(saved.getId());
        response.setEntryTimestamp(saved.getEntryTimestamp());
        response.setSuccess(saved.isSuccess());
        response.setScheduling(schedulingMapper.toResponseDTO(saved.getScheduling()));

        return response;
    }
    
    public List<AccessLogResponseDTO> getAllAccessLogs() {
        List<AccessLog> accessLogs = accessLogRepository.findAll();
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
