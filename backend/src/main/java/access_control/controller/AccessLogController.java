package access_control.controller;

import access_control.dto.AccessLogRequestDTO;
import access_control.dto.AccessLogResponseDTO;
import access_control.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/accessLogs")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @PostMapping
    public ResponseEntity<AccessLogResponseDTO> createAccessLog(
            @RequestBody AccessLogRequestDTO dto,
            Authentication authentication) {
        AccessLogResponseDTO response = accessLogService.createAccessLog(dto, authentication.getName());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccessLogResponseDTO>> getAllAccessLogs(Authentication authentication) {
        List<AccessLogResponseDTO> response = accessLogService.getAllAccessLogs(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessLogResponseDTO> getAccessLogById(@PathVariable Long id) {
        AccessLogResponseDTO response = accessLogService.getAccessLogById(id);
        return ResponseEntity.ok(response);
    }
}
