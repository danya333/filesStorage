package com.mrv.filesstorageapp.controllers;

import com.mrv.filesstorageapp.DTOs.JwtAuthenticationDTO;
import com.mrv.filesstorageapp.DTOs.SignInDTO;
import com.mrv.filesstorageapp.DTOs.SignUpDTO;
import com.mrv.filesstorageapp.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationDTO> signIn(
            @RequestBody SignInDTO signInDTO
    ) {
        return ResponseEntity.ok(authenticationService.signIn(signInDTO));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationDTO> signUp(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "logo", required = false) MultipartFile logo
    ) {
        SignUpDTO signUpDTO = new SignUpDTO(name, surname, username, password, logo);
        return ResponseEntity.ok(authenticationService.signUp(signUpDTO));
    }
}
