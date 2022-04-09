package com.together.community.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "menu")
@Getter @Setter
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    private String name;

}