package access_control.controller;

import access_control.dto.AppUserRequestDTO;
import access_control.dto.AppUserResponseDTO;
import access_control.service.AppUserService;
import access_control.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

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

    @GetMapping("/me")
    public ResponseEntity<AppUserResponseDTO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        AppUserResponseDTO response = appUserService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }
}
