package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.ErrorEmptyObjectException;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private RsService rsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteService voteService;

    public UserDto getUserDtoById(int userId) {
        if(!userRepository.findById(userId).isPresent()){
            throw new ErrorEmptyObjectException("Unknown UserID");
        }
        return userRepository.findById(userId).get();
    }

    public void addNewUser(User user) {
        UserDto userDto = UserDto.builder().userName(user.getUserName())
                .age(user.getAge()).email(user.getEmail()).gender(user.getGender())
                .phone(user.getPhone()).build();
        addNewUser(userDto);
    }

    public void addNewUser(UserDto userDto) {
        userRepository.save(userDto);
    }

    public List<UserDto> getAllUsersDto() {
        return userRepository.findAll();
    }

    public List<User> getAllUsers() {
        return getAllUsersDto().stream().map(userDto ->
                User.builder().voteNum(userDto.getVoteNum())
                        .userName(userDto.getUserName())
                        .age(userDto.getAge())
                        .email(userDto.getEmail())
                        .gender(userDto.getGender())
                        .phone(userDto.getPhone()).build())
                .collect(Collectors.toList());
    }

    public User getUserById(int userId) {
        UserDto userDto = getUserDtoById(userId);
        return User.builder().voteNum(userDto.getVoteNum())
                .userName(userDto.getUserName())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .gender(userDto.getGender())
                .phone(userDto.getPhone()).build();
    }

    public void deleteUserById(int userId) {
        try{
            userRepository.deleteById(userId);
        }catch (Exception e){
            throw new Error("error userId");
        }
    }
}
