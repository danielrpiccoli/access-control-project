package access_control.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRequestDTO {
    private String name;
    private String email;
    private String password;
}
