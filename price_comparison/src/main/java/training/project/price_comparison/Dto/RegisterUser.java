package training.project.price_comparison.Dto;

import lombok.Data;
import training.project.price_comparison.model.Role;

@Data
public class RegisterUser {
    private String username;
    private String password;
    private Role role;
    private String email;
}
