package com.levelup.core.domain.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(VotePk.class)
public class Vote {

    @Id
    private Long memberId;

    @Id
    private Long targetId;

    @Id
    @Enumerated(EnumType.STRING)
    private VoteType voteType;
}
