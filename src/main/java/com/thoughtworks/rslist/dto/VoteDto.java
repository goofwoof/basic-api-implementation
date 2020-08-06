package com.thoughtworks.rslist.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "vote")
@DynamicUpdate
@DynamicInsert
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(targetEntity = RsEventDto.class)
    private RsEventDto rsEventDto;

    private int voutNum;

    @ManyToOne(targetEntity = UserDto.class)
    private UserDto userDtoVote;

    @Column(name="voteTime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp voteTime;

}
