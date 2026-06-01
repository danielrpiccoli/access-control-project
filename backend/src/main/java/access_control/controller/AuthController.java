package access_control.controller;

import access_control.dto.AppUserRequestDTO;
import access_control.dto.AppUserResponseDTO;
import access_control.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<AppUserResponseDTO> register(@RequestBody AppUserRequestDTO dto) {
        AppUserResponseDTO response = appUserService.createUser(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUserRequestDTO dto) {
        String token = appUserService.loginUser(dto);
        return ResponseEntity.ok(token);
    }
}
