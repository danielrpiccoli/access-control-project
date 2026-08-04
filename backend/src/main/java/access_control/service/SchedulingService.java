package access_control.service;

import access_control.dto.SchedulingRequestDTO;
import access_control.dto.SchedulingResponseDTO;
import access_control.entity.Scheduling;
import access_control.mapper.AppUserMapper;
import access_control.mapper.SpaceMapper;
import access_control.repository.AppUserRepository;
import access_control.repository.SchedulingRepository;
import access_control.repository.SpaceRepository;
import access_control.entity.AppUser;
import access_control.entity.Space;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class SchedulingService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private SpaceMapper spaceMapper;

    public SchedulingResponseDTO createScheduling(SchedulingRequestDTO dto, String email) {
        AppUser user = appUserRepository.findByEmail(email);
        Space space = spaceRepository.findById(dto.getSpaceId()).orElseThrow();

        if (!dto.getStartTime().isBefore(dto.getEndTime())) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }

        List<Scheduling> overlapping = schedulingRepository.findOverlapping(
                space, dto.getScheduledDate(), dto.getStartTime(), dto.getEndTime());
        if (!overlapping.isEmpty()) {
            throw new IllegalStateException("Space is already booked for this time slot");
        }

        Scheduling scheduling = new Scheduling();
        scheduling.setScheduledDate(dto.getScheduledDate());
        scheduling.setStartTime(dto.getStartTime());
        scheduling.setEndTime(dto.getEndTime());
        scheduling.setStatus("PENDING");
        scheduling.setUser(user);
        scheduling.setSpace(space);

        Scheduling saved = schedulingRepository.save(scheduling);
        return toResponseDTO(saved);
    }

    public List<SchedulingResponseDTO> getAllSchedulings(String email) {
        AppUser requester = appUserRepository.findByEmail(email);
        List<Scheduling> schedulings = "ADMIN".equals(requester.getRole())
                ? schedulingRepository.findAll()
                : schedulingRepository.findByUser(requester);
        List<SchedulingResponseDTO> response = new ArrayList<>();
        for (Scheduling scheduling : schedulings) {
            response.add(toResponseDTO(scheduling));
        }
        return response;
    }

    public SchedulingResponseDTO getSchedulingById(Long id) {
        Scheduling scheduling = schedulingRepository.findById(id).orElseThrow();
        return toResponseDTO(scheduling);
    }

    public SchedulingResponseDTO cancelScheduling(Long id, String email) {
        Scheduling scheduling = schedulingRepository.findById(id).orElseThrow();
        AppUser requester = appUserRepository.findByEmail(email);

        boolean isOwner = scheduling.getUser().getId().equals(requester.getId());
        boolean isAdmin = "ADMIN".equals(requester.getRole());
        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("This scheduling does not belong to you");
        }

        if ("CANCELLED".equals(scheduling.getStatus())) {
            throw new IllegalStateException("Scheduling is already cancelled");
        }

        scheduling.setStatus("CANCELLED");
        Scheduling saved = schedulingRepository.save(scheduling);
        return toResponseDTO(saved);
    }

    private SchedulingResponseDTO toResponseDTO(Scheduling scheduling) {
        SchedulingResponseDTO response = new SchedulingResponseDTO();
        response.setId(scheduling.getId());
        response.setScheduledDate(scheduling.getScheduledDate());
        response.setStartTime(scheduling.getStartTime());
        response.setEndTime(scheduling.getEndTime());
        response.setStatus(scheduling.getStatus());
        response.setUser(appUserMapper.toResponseDTO(scheduling.getUser()));
        response.setSpace(spaceMapper.toResponseDTO(scheduling.getSpace()));
        return response;
    }
}
