package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    @Autowired
    private RsService rsService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteRepository voteRepository;
}
