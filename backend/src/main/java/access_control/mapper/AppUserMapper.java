package access_control.mapper;

import access_control.entity.AppUser;
import access_control.dto.AppUserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {
    public AppUserResponseDTO toResponseDTO(AppUser user) {
        AppUserResponseDTO response = new AppUserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }
}
