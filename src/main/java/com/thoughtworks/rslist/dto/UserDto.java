package com.thoughtworks.rslist.dto;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.thoughtworks.rslist.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "user")
@DynamicUpdate
@DynamicInsert
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class UserDto {
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String gender;
    private int age;
    private String email;
    private String phone;
    private int voteNum;

    @OneToMany(targetEntity = RsEventDto.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private List<RsEventDto> rsEventDtoList;

    @OneToMany(targetEntity = VoteDto.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private  List<VoteDto> voteDtoList;
}
