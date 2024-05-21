package com.modernfarmer.farmusspring.domain.user.repository;

import com.modernfarmer.farmusspring.domain.user.entity.UserFirebaseToken;
import com.modernfarmer.farmusspring.domain.user.entity.UserMotivation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FirebaseTokenRepository extends JpaRepository<UserFirebaseToken, Long> {

    @Modifying
    @Query(value = "DELETE FROM user_firebase_token AS uft WHERE uft.token = :firebaseToken and uft.user.id = :userId")
    void deleteToken(@Param("firebaseToken") String firebaseToken, @Param("userId") Long userId);

}
