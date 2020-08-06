package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RsEvent {
    @NotNull
    private String eventName;
    @NotNull
    private String keyWord;
    @NotNull
    private int userId;
}
