package com.mrv.filesstorageapp.controllers;

import com.mrv.filesstorageapp.models.User;
import com.mrv.filesstorageapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public User getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        user.setPassword(null);
        user.setAlbums(null);
        user.setLogo("/logos/" + user.getLogo());
        return user;
    }


//    @PostMapping("/create")
//    public ResponseEntity<?> createUser(@RequestParam("name") String name,
//                                        @RequestParam("surname") String surname,
//                                        @RequestParam("username") String username,
//                                        @RequestParam("password") String password,
//                                        @RequestParam(value = "logo", required = false) MultipartFile logo,
//                                        UriComponentsBuilder uriComponentsBuilder) {
//
//        SignUpDTO signUpDTO = new SignUpDTO(name, surname, username, password, logo);
//        User user = userService.createUser(signUpDTO);
//        user.setPassword(null);
//
//        return ResponseEntity
//                .created(uriComponentsBuilder
//                        .replacePath("/storage/user/{id}")
//                        .build(Map.of("id", user.getId())))
//                .body(user);
//    }
}
