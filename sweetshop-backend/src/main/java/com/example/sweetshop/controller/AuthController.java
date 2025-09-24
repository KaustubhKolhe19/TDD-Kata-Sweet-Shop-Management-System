package com.example.sweetshop.controller;

import com.example.sweetshop.model.Role;
import com.example.sweetshop.model.User;
import com.example.sweetshop.model.Role.ERole;
import com.example.sweetshop.payload.request.LoginRequest;
import com.example.sweetshop.payload.request.RegisterRequest;
import com.example.sweetshop.payload.response.JwtResponse;
import com.example.sweetshop.repository.RoleRepository;
import com.example.sweetshop.repository.UserRepository;
import com.example.sweetshop.security.JwtUtils;
import com.example.sweetshop.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;

  public AuthController(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder encoder,
                        JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.encoder = encoder;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

    if (userRepository.existsByUsername(registerRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(registerRequest.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }

    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(encoder.encode(registerRequest.getPassword()));

    Set<Role> roles = new HashSet<>();

    String strRole = registerRequest.getRole();
    if (strRole == null || strRole.isEmpty()) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else if ("admin".equalsIgnoreCase(strRole)) {
      Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(adminRole);
    } else {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    }

    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok("User registered successfully!");
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(
        jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }
}
