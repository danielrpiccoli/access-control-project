package access_control.service;


import access_control.dto.AppUserRequestDTO;
import access_control.dto.AppUserResponseDTO;
import access_control.entity.AppUser;
import access_control.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;

    public AppUserResponseDTO createUser(AppUserRequestDTO dto) {
        AppUser user = new AppUser();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole("USER");

        AppUser saved = appUserRepository.save(user);

        AppUserResponseDTO response = new AppUserResponseDTO();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setRole(saved.getRole());

        return response;
    }
}
