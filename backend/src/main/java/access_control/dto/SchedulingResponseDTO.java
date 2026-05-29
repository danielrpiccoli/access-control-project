package access_control.dto;

import java.time.LocalTime;
import java.time.LocalDate;

public class SchedulingResponseDTO {
    private Long id;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private AppUserResponseDTO user;
    private SpaceResponseDTO space;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AppUserResponseDTO getUser() {
        return user;
    }

    public void setUser(AppUserResponseDTO user) {
        this.user = user;
    }

    public SpaceResponseDTO getSpace() {
        return space;
    }

    public void setSpace(SpaceResponseDTO space) {
        this.space = space;
    }
}