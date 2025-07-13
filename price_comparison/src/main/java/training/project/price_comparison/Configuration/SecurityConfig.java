package training.project.price_comparison.Configuration;

//package price.comparison.training_project.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Came her for security check");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/products/**").permitAll() // public endpoints
                        .anyRequest().authenticated() // secure all other endpoints
                )
                .httpBasic(Customizer.withDefaults()); // or use JWT/custom filter

        return http.build();
    }

    // Define PasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("Came here first Security config");
        return new BCryptPasswordEncoder();
    }
}
