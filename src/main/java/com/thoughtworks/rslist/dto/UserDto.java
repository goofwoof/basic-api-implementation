package com.thoughtworks.rslist.dto;


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

    @OneToMany(targetEntity=RsEventDto.class, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, mappedBy = "userDto")
    private List<RsEventDto> rsEventDtoList;
}
