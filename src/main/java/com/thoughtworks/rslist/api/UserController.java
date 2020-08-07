package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody @Valid User user){
        userService.addNewUser(user);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/users")
    public ResponseEntity getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/get/user/{index}")
    public ResponseEntity getUser(@PathVariable int index){
        return ResponseEntity.ok(userService.getUserById(index));
    }

    @PostMapping("/user/delete/{index}")
    public ResponseEntity deleteUser(@PathVariable int index){
        userService.deleteUserById(index);
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
