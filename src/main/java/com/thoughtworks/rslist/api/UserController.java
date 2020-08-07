package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody @Valid User user){
        UserDto userDto = UserDto.builder().userName(user.getUserName())
                .age(user.getAge()).email(user.getEmail()).gender(user.getGender())
                .phone(user.getPhone()).build();
        userRepository.save(userDto);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/users")
    public ResponseEntity getUsers(){

        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/get/user/{index}")
    public ResponseEntity getUser(@PathVariable int index){

        return ResponseEntity.ok(userRepository.findById(index));
    }

    @PostMapping("/user/delete/{index}")
    public ResponseEntity deleteUser(@PathVariable int index){
        try{
            userRepository.deleteById(index);
        }catch (Exception e){
            return ResponseEntity.badRequest().header("error", "error index").body(e);
        }
        return ResponseEntity.ok(userRepository.findAll());
    }
}
