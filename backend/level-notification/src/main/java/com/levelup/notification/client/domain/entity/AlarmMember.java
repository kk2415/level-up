package com.levelup.notification.client.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "alarm_member")
@Entity
public class AlarmMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_member_id")
    private Long id;

    private Long memberId;
    private String name;
}
