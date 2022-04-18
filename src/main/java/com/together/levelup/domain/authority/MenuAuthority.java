//package com.together.community.domain.authority;
//
//import com.together.community.domain.Menu;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter @Setter
//public class MenuAuthority {
//
//    @Id @GeneratedValue
//    @Column(name = "menu_authority_id")
//    private Long menuAuthorityId;
//
//    private String authority;
//
//    @ManyToOne
//    @JoinColumn(name = "authority_group_id")
//    private AuthorityGroup authorityGroup;
//
//    @ManyToOne
//    @JoinColumn(name = "menu_id")
//    private Menu menu;
//
//    //==연관관계 메서드==//
//    public void setMenu(Menu menu) {
//        this.menu = menu;
//        menu.getMenuAuthorities().add(this);
//    }
//
//}
