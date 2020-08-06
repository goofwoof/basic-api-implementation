package com.thoughtworks.rslist.dto;


import com.thoughtworks.rslist.domain.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
@Data
public class UserDto {
    @Id
    private int id;
    private String userName;
    private String gender;
    private int age;
    private String email;
    private String phone;
    private int voteNum;

    public UserDto(User user) {
        this.age = user.getAge();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.voteNum = user.getVoteNum();
        this.userName = user.getUserName();
    }

    public UserDto() {
    }
}
