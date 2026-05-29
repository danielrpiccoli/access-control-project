package access_control.dto;

import java.time.LocalDateTime;

public class AccessLogResponseDTO {
    private Long id;
    private LocalDateTime entryTimestamp;
    private boolean success;
    private SchedulingResponseDTO scheduling;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEntryTimestamp() {
        return entryTimestamp;
    }

    public void setEntryTimestamp(LocalDateTime entryTimestamp) {
        this.entryTimestamp = entryTimestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public SchedulingResponseDTO getScheduling() {
        return scheduling;
    }

    public void setScheduling(SchedulingResponseDTO scheduling) {
        this.scheduling = scheduling;
    }
}
