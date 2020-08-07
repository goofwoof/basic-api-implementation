package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.WiselyRepository.WiselyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoteRepository extends WiselyRepository<VoteDto, Integer> {
    @Override
    List<VoteDto> findAll();

    @Override
    Page<VoteDto> findAll(Pageable pageable);

}
