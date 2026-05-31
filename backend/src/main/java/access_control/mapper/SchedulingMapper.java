package access_control.mapper;

import access_control.entity.Scheduling;
import access_control.dto.SchedulingResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulingMapper {

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private SpaceMapper spaceMapper;

    public SchedulingResponseDTO toResponseDTO(Scheduling scheduling) {
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
