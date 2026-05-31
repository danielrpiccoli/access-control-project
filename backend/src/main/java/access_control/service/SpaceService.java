package access_control.service;

import access_control.dto.SpaceRequestDTO;
import access_control.dto.SpaceResponseDTO;
import access_control.entity.Space;
import access_control.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

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

    public List<SpaceResponseDTO> getAllSpaces() {
        List<Space> spaces = spaceRepository.findAll();
        List<SpaceResponseDTO> response = new ArrayList<>();
        for (Space space : spaces) {
            SpaceResponseDTO dto = new SpaceResponseDTO();
            dto.setId(space.getId());
            dto.setName(space.getName());
            dto.setCapacity(space.getCapacity());
            dto.setDescription(space.getDescription());
            dto.setStatus(space.getStatus());
            response.add(dto);
        }
        return response;
    }

    public SpaceResponseDTO getSpaceById(Long id) {
        Space space = spaceRepository.findById(id).orElseThrow();
        SpaceResponseDTO response = new SpaceResponseDTO();
        response.setId(space.getId());
        response.setName(space.getName());
        response.setCapacity(space.getCapacity());
        response.setDescription(space.getDescription());
        response.setStatus(space.getStatus());
        return response;
    }
}
