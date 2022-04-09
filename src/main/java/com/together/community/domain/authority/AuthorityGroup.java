//package com.together.community.domain.authority;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "authority_group")
//@Getter @Setter
//public class AuthorityGroup {
//
//    @Id @GeneratedValue
//    @Column(name = "authority_group_id")
//    private Long authorityGroupId;
//
//    private String name;
//
//    @OneToMany(mappedBy = "authorityGroup")
//    private List<MenuAuthority> menuAuthorities = new ArrayList<>();
//
//    @OneToMany(mappedBy = "authorityGroup")
//    private List<MemberAuthority> memberAuthorities = new ArrayList<>();
//
//    //==연관관계 메서드==//
//    public void setMemberAuthorities(MemberAuthority memberAuthority) {
//        this.memberAuthorities.add(memberAuthority);
//        memberAuthority.setAuthorityGroup(this);
//    }
//
//    public void setMenuAuthority(MenuAuthority menuAuthority) {
//        this.menuAuthorities.add(menuAuthority);
//        menuAuthority.setAuthorityGroup(this);
//    }
//
//}
