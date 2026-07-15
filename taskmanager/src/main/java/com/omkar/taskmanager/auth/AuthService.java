package com.omkar.taskmanager.auth;

import com.omkar.taskmanager.security.JwtService;
import com.omkar.taskmanager.user.User;
import com.omkar.taskmanager.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user= User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .message("User Registered Successfully")
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest){
        User user=userRepository.findByEmail(loginRequest.getEmail())
                        .orElseThrow(()-> new RuntimeException("Invalid email or password"));
        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new RuntimeException("Invald email or password");
        }
        return AuthResponse.builder()
                .message("Login Successful")
                .token(jwtService.generateToken(user))
                .build();
    }
}
