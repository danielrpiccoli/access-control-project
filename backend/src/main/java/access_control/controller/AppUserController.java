package access_control.controller;

import access_control.dto.AppUserRequestDTO;
import access_control.dto.AppUserResponseDTO;
import access_control.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @PostMapping
    public ResponseEntity<AppUserResponseDTO> createUser(@RequestBody AppUserRequestDTO dto) {
        AppUserResponseDTO response = appUserService.createUser(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponseDTO>> getAllUsers(Authentication authentication) {
        List<AppUserResponseDTO> response = appUserService.getAllUsers(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponseDTO> getUSerById(@PathVariable Long id) {
        AppUserResponseDTO response = appUserService.getUserById(id);
        return ResponseEntity.ok(response);
    }
}
