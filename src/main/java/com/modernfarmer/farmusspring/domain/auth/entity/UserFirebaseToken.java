//package com.modernfarmer.farmusspring.domain.auth.entity;
//
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//import main.java.com.modernfarmer.farmusspring.common.BaseEntity;
//
//
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@Getter
//@SuperBuilder
//@Entity(name = "user_firebase_token")
//public class UserFirebaseToken extends BaseEntity {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "firebase_token_id")
//    private Long id;
//
//
//    @Column(name = "token")
//    private String token;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    public static UserFirebaseToken createUserFirebaseToken(String token, User user){
//        UserFirebaseToken newUserFirebaseToken = UserFirebaseToken.builder()
//                .token(token)
//                .user(user)
//                .build();
//
//        user.addUserFirebaseToken(newUserFirebaseToken);
//
//        return newUserFirebaseToken;
//
//    }
//
//}
