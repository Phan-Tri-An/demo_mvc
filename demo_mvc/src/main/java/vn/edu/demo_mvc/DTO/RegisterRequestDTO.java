package vn.edu.demo_mvc.DTO;

import lombok.Data;
import vn.edu.demo_mvc.enums.Role;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private Role role;
}