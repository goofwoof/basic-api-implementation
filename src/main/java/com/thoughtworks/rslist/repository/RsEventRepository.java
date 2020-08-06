package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.RsEventDto;
import org.springframework.data.repository.CrudRepository;
public interface RsEventRepository extends CrudRepository<RsEventDto, Integer> {
}
