package com.modernfarmer.farmusspring.domain.user.entity;


import com.modernfarmer.farmusspring.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;




@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@Entity(name = "user_motivation")
public class UserMotivation extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_motivation_id")
    private Long id;


    @Column(name = "motivation")
    private String motivation;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public static UserMotivation createUserMotivation(String motivation, User user){
        UserMotivation newUserMotivation = UserMotivation.builder()
                .motivation(motivation)
                .user(user)
                .build();

        user.addUserMotivation(newUserMotivation);

        return newUserMotivation;

    }

}
