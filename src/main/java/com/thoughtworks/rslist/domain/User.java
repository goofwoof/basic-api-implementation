package com.thoughtworks.rslist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;


@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull
    @Size(max = 8, min = 1)
    private String userName;
    @NotNull
    private String gender;
    @NotNull
    @Min(18)
    @Max(100)
    private int age;
    @Email
    private String email;
    @Pattern(regexp = "1\\d{10}")
    private String phone;
    private int voteNum = 10;
}
