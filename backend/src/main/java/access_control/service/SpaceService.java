package access_control.service;

import access_control.dto.SpaceRequestDTO;
import access_control.dto.SpaceResponseDTO;
import access_control.entity.Space;
import access_control.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SpaceService {
    @Autowired
    private SpaceRepository spaceRepository;

    public SpaceResponseDTO createSpace(SpaceRequestDTO dto){
        Space space = new Space();
        space.setName(dto.getName());
        space.setCapacity(dto.getCapacity());
        space.setDescription(dto.getDescription());
        space.setStatus(dto.getStatus());

        Space saved = spaceRepository.save(space);

        SpaceResponseDTO response = new SpaceResponseDTO();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setCapacity(saved.getCapacity());
        response.setDescription(saved.getDescription());
        response.setStatus(saved.getStatus());

        return response;
    }
}
