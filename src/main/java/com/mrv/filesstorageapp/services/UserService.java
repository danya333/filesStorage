package com.mrv.filesstorageapp.services;

import com.mrv.filesstorageapp.DTOs.SignUpDTO;
import com.mrv.filesstorageapp.models.User;
import com.mrv.filesstorageapp.models.UserRoles;
import com.mrv.filesstorageapp.repositories.UserRepository;
import com.mrv.filesstorageapp.security.UserDetailsServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${logo.path}")
    private String logosDir;

    public User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElse(null);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public User createUser(SignUpDTO signUpDTO) {
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setSurname(signUpDTO.getSurname());
        user.setRole(UserRoles.USER);
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        try {
            user.setLogo(saveLogo(signUpDTO.getLogo()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(user);
        return user;
    }

    public void updateUser(Long id, SignUpDTO signUpDTO) {
        User user = getUserById(id);
        user.setName(signUpDTO.getName());
        user.setSurname(signUpDTO.getSurname());
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        try {
            user.setLogo(saveLogo(signUpDTO.getLogo()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private String saveLogo(MultipartFile file) throws IOException {
        if (file == null){
            return null;
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String newFileName = timeStamp + "_" + fileName;

        Path path = Paths.get(logosDir).resolve(newFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return newFileName;
    }

}
