package access_control.dto;

public class AccessLogRequestDTO {
    private Long schedulingId;
    private boolean success;

    public Long getSchedulingId() {
        return schedulingId;
    }
    public void setSchedulingId(Long schedulingId) {
        this.schedulingId = schedulingId;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
}