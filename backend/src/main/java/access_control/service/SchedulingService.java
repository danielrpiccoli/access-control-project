package access_control.service;

import access_control.dto.SchedulingRequestDTO;
import access_control.dto.SchedulingResponseDTO;
import access_control.dto.AppUserResponseDTO;
import access_control.dto.SpaceResponseDTO;
import access_control.entity.Scheduling;
import access_control.repository.AppUserRepository;
import access_control.repository.SchedulingRepository;
import access_control.repository.SpaceRepository;
import access_control.entity.AppUser;
import access_control.entity.Space;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private SchedulingRepository schedulingRepository;

    public SchedulingResponseDTO createScheduling(SchedulingRequestDTO dto) {
        AppUser user = appUserRepository.findById(dto.getUserId()).orElseThrow();
        Space space = spaceRepository.findById(dto.getSpaceId()).orElseThrow();

        Scheduling scheduling = new Scheduling();
        scheduling.setScheduledDate(dto.getScheduledDate());
        scheduling.setStartTime(dto.getStartTime());
        scheduling.setEndTime(dto.getEndTime());
        scheduling.setStatus("PENDING");
        scheduling.setUser(user);
        scheduling.setSpace(space);

        Scheduling saved = schedulingRepository.save(scheduling);

        SchedulingResponseDTO response = new SchedulingResponseDTO();
        response.setId(saved.getId());
        response.setScheduledDate(saved.getScheduledDate());
        response.setStartTime(saved.getStartTime());
        response.setEndTime(saved.getEndTime());
        response.setStatus(saved.getStatus());
        response.setUser(toUserResponse(saved.getUser()));
        response.setSpace(toSpaceResponse(saved.getSpace()));

        return response;
    }

    private AppUserResponseDTO toUserResponse(AppUser user) {
        AppUserResponseDTO response = new AppUserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }

    private SpaceResponseDTO toSpaceResponse(Space space) {
        SpaceResponseDTO response = new SpaceResponseDTO();
        response.setId(space.getId());
        response.setName(space.getName());
        response.setCapacity(space.getCapacity());
        response.setDescription(space.getDescription());
        response.setStatus(space.getStatus());
        return response;
    }
}
