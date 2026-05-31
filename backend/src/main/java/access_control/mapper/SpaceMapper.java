package access_control.mapper;

import access_control.entity.Space;
import access_control.dto.SpaceResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class SpaceMapper {
    public SpaceResponseDTO toResponseDTO(Space space) {
        SpaceResponseDTO response = new SpaceResponseDTO();
        response.setId(space.getId());
        response.setName(space.getName());
        response.setCapacity(space.getCapacity());
        response.setDescription(space.getDescription());
        response.setStatus(space.getStatus());
        return response;
    }
}
