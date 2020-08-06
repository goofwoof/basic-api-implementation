package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rsEvent")
@DynamicUpdate
@DynamicInsert
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RsEventDto {
    @Id
    @GeneratedValue
    private int id;
    private String eventName;
    private String keyWord;
    @ManyToOne(targetEntity = UserDto.class)
    @JoinColumn(name = "user_id")
    private UserDto userDtoRS;

    @OneToMany//(targetEntity = VoteDto.class, cascade = {CascadeType.REMOVE}, mappedBy = "rsEventDto")
    @JoinColumn(name = "rs_id")
    private List<VoteDto> voteDtoList;
}
