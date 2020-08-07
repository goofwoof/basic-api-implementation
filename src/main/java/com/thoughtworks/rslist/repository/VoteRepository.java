package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.VoteDto;

import org.hibernate.mapping.Collection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface VoteRepository extends CrudRepository<VoteDto, Integer> {
    @Override
    List<VoteDto> findAll();

    List<VoteDto> findAll(Pageable pageable);

    List<VoteDto> findAllByVoteTimeIn(List<Timestamp> times);

    List<VoteDto> findAllByVoteTimeBetween(Timestamp start, Timestamp end);

}
