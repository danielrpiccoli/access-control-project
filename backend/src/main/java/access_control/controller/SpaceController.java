package access_control.controller;

import access_control.dto.SpaceRequestDTO;
import access_control.dto.SpaceResponseDTO;
import access_control.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @PostMapping
    public ResponseEntity<SpaceResponseDTO> createSpace(@RequestBody SpaceRequestDTO dto) {
        SpaceResponseDTO response = spaceService.createSpace(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SpaceResponseDTO>> getAllSpaces() {
        List<SpaceResponseDTO> response = spaceService.getAllSpaces();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceResponseDTO> getSpaceById(@PathVariable Long id) {
        SpaceResponseDTO response = spaceService.getSpaceById(id);
        return ResponseEntity.ok(response);
    }
}
