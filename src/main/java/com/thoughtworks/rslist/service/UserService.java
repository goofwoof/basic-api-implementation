package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.ErrorEmptyObjectException;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
