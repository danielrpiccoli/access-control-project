package access_control.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceRequestDTO {
    private String name;
    private int capacity;
    private String description;
    private String status;

}