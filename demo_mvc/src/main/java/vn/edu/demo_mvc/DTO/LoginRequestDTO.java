package vn.edu.demo_mvc.DTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}