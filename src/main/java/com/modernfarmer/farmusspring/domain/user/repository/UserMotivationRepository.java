package com.modernfarmer.farmusspring.domain.user.repository;


import com.modernfarmer.farmusspring.domain.user.entity.UserMotivation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserMotivationRepository extends JpaRepository<UserMotivation, Long> {

    @Modifying
    @Query(value = "INSERT INTO user_motivation(motivation, user_id) VALUES (:motivation, :userId)", nativeQuery = true)
    void insertMotivation(@Param("motivation") String motivation, @Param("userId") Long userId);

}
