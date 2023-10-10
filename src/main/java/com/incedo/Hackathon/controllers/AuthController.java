package com.incedo.Hackathon.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import com.incedo.Hackathon.constants.UserRoleConstants;
import com.incedo.Hackathon.models.Team;
import com.incedo.Hackathon.repository.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.incedo.Hackathon.jwt.JwtUtils;
import com.incedo.Hackathon.payload.response.JwtResponse;
import com.incedo.Hackathon.payload.response.MessageResponse;
import com.incedo.Hackathon.repository.RoleRepository;
import com.incedo.Hackathon.repository.UserRepository;
import com.incedo.Hackathon.services.UserDetailsImpl;
import com.incedo.Hackathon.models.ERole;
import com.incedo.Hackathon.models.Role;
import com.incedo.Hackathon.models.User;
import com.incedo.Hackathon.payload.request.LoginRequest;
import com.incedo.Hackathon.payload.request.SignupRequest;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhoneNo(),
                signUpRequest.getName()
        );

        Set<String> strRoles = roleCompatibleForUI(signUpRequest.getRole());
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            return ResponseEntity.status(401).body("Role should not be empty!");
        } else {
            try {
                strRoles.forEach(role -> {
                    switch (role) {
                        case UserRoleConstants.ADMIN:
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);

                            break;
                        case UserRoleConstants.PARTICIPANT:
                            Role participantRole = roleRepository.findByName(ERole.ROLE_PARTICIPANT)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(participantRole);

                            break;
                        case UserRoleConstants.PANELIST:
                            Role panelistRole = roleRepository.findByName(ERole.ROLE_PANELIST)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(panelistRole);

                            break;
                        case UserRoleConstants.JUDGE:
                            Role judgeRole = roleRepository.findByName(ERole.ROLE_JUDGE)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(judgeRole);

                            break;
                        default:
                            throw new RuntimeException("Error: Role is not supported. Please use admin , participant or panelist");
                    }
                });
            } catch (RuntimeException e) {
                return ResponseEntity.status(401).body(e.getMessage());
            }

        }
        if (signUpRequest.getRole().equals(UserRoleConstants.TEAM_LEADER)) {
                Team t = new Team();
                t.setTeamName(signUpRequest.getTeamName());
                t = teamRepository.save(t);
                user.setTeam(t);
            } else if (signUpRequest.getRole().equals(UserRoleConstants.TEAM_MEMBER)){
                if(signUpRequest.getTeamId().isBlank()){
                    return ResponseEntity.status(401).body("Team id not found!!");
                }
                Long teamId;
                try {
                    teamId = Long.valueOf(signUpRequest.getTeamId());
                }catch (Exception e){
                    return ResponseEntity.status(401).body("Team id not right format!");
                }
                Optional<Team> t = teamRepository.findById(teamId);
                if (t.isPresent()) {
                    user.setTeam(t.get());
                } else {
                    return ResponseEntity.status(401).body("Team not found!!");
                }
        }


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    private Set<String> roleCompatibleForUI(String role) {
        return switch (role) {
            case UserRoleConstants.TEAM_LEADER, UserRoleConstants.TEAM_MEMBER -> Set.of(UserRoleConstants.PARTICIPANT);
            case UserRoleConstants.PANELIST -> Set.of(UserRoleConstants.PANELIST);
            case UserRoleConstants.JUDGE -> Set.of(UserRoleConstants.JUDGE);
            default -> Set.of();
        };
    }
}
