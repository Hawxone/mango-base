package com.weekend.mango.controllers;


import com.weekend.mango.entities.RoleEntity;
import com.weekend.mango.entities.UserEntity;
import com.weekend.mango.payload.request.LoginRequest;
import com.weekend.mango.payload.request.SignupRequest;
import com.weekend.mango.payload.response.JwtResponse;
import com.weekend.mango.payload.response.MessageResponse;
import com.weekend.mango.repositories.RoleEntityRepository;
import com.weekend.mango.repositories.UserEntityRepository;
import com.weekend.mango.security.jwt.JwtUtils;
import com.weekend.mango.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserEntityRepository userEntityRepository;

    private final RoleEntityRepository roleEntityRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;


    public AuthController(AuthenticationManager authenticationManager, UserEntityRepository userEntityRepository, RoleEntityRepository roleEntityRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userEntityRepository = userEntityRepository;
        this.roleEntityRepository = roleEntityRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateuser(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getImageUrl(),
                roles
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){

        if (userEntityRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("error: username is already taken"));
        }

        if (userEntityRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : email is already in use"));
        }

        UserEntity user = new UserEntity();
        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        user.setImageUrl("");
        user.setPassword(encoder.encode(signupRequest.getPassword()));


        Set<String> strRoles = signupRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();


        if (strRoles == null){
            RoleEntity userRole = roleEntityRepository.findByName("USER")
                    .orElseThrow(()-> new RuntimeException("Role is not found"));
            roles.add(userRole);
        }else{

            strRoles.forEach(role-> {
                switch (role) {
                    case "admin" -> {
                        RoleEntity adminRole = roleEntityRepository.findByName("ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        RoleEntity modRole = roleEntityRepository.findByName("MODERATOR")
                                .orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        roles.add(modRole);
                    }
                    default -> {
                        RoleEntity userRole = roleEntityRepository.findByName("USER")
                                .orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        roles.add(userRole);
                    }
                }
            });

        }
        user.setRoles(roles);
        userEntityRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("user registered successfully"));
    }

}
