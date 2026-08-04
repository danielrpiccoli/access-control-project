package access_control.controller;

import access_control.dto.SchedulingRequestDTO;
import access_control.dto.SchedulingResponseDTO;
import access_control.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/schedulings")
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;

    @PostMapping
    public ResponseEntity<SchedulingResponseDTO> createScheduling(
            @RequestBody SchedulingRequestDTO dto,
            Authentication authentication) {
        SchedulingResponseDTO response = schedulingService.createScheduling(dto, authentication.getName());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SchedulingResponseDTO>> getAllSchedulings() {
        List<SchedulingResponseDTO> response = schedulingService.getAllSchedulings();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulingResponseDTO> getSchedulingById(@PathVariable Long id) {
        SchedulingResponseDTO response = schedulingService.getSchedulingById(id);
        return ResponseEntity.ok(response);
    }
}
