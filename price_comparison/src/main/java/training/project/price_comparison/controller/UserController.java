package training.project.price_comparison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training.project.price_comparison.Dto.RegisterUser;
import training.project.price_comparison.Repository.UserRepository;
import training.project.price_comparison.model.User;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUser registerUSer) {
        User user = User.builder()
                .username(registerUSer.getUsername())
                .password(passwordEncoder.encode(registerUSer.getPassword()))
                .email(registerUSer.getEmail())
                .role(registerUSer.getRole())
                .build();
        userRepository.save(user);
        return ResponseEntity.ok("User Registered Successfully");
    }

//    @GetMapping("/login")
//    public ResponseEntity<User> loginUser(@RequestBody AuthRequest authRequest) {
//
//    }
}
