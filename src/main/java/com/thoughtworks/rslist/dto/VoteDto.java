package com.thoughtworks.rslist.dto;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class VoteDto {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(targetEntity = RsEventDto.class)
    @JoinColumn(name = "rs_id")
    private RsEventDto rsEventDto;

    private int voutNum;

    @ManyToOne(targetEntity = UserDto.class)
    @JoinColumn(name = "user_id")
    private UserDto userDtoVote;

    @Column(name="voteTime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp voteTime;

}
