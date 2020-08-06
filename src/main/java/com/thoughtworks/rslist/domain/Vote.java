package com.thoughtworks.rslist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;

@Component
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote {
    private int rsEventId;
    private int userId;
    private int voteNum;
    private Timestamp voteTime;
}
