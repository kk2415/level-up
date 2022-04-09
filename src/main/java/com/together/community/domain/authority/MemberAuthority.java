//package com.together.community.domain.authority;
//
//import com.together.community.domain.Member.Member;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//import static javax.persistence.FetchType.LAZY;
//
//@Entity
//@Table(name = "member_authority")
//@Getter @Setter
//public class MemberAuthority {
//
//    @Id @GeneratedValue
//    @Column(name = "member_authority_id")
//    private Long memberAuthorityId;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "authority_group_id")
//    private AuthorityGroup authorityGroup;
//
//}
