package com.mrv.filesstorageapp.services;

import com.mrv.filesstorageapp.DTOs.JwtAuthenticationDTO;
import com.mrv.filesstorageapp.DTOs.SignInDTO;
import com.mrv.filesstorageapp.DTOs.SignUpDTO;
import com.mrv.filesstorageapp.models.User;
import com.mrv.filesstorageapp.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtAuthenticationDTO signUp(SignUpDTO signUpDTO) {

        User user = userService.createUser(signUpDTO);

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUsername());

        String token = jwtService.generateToken(userDetails);
        return new JwtAuthenticationDTO(token);
    }

    public JwtAuthenticationDTO signIn(SignInDTO signInDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), signInDTO.getPassword())
        );

        UserDetails user = userDetailsServiceImpl.loadUserByUsername(signInDTO.getUsername());

        String token = jwtService.generateToken(user);
        return new JwtAuthenticationDTO(token);
    }
}
