package com.modernfarmer.farmusspring.domain.user.repository;


import com.modernfarmer.farmusspring.domain.user.entity.UserMotivation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserMotivationRepository extends JpaRepository<UserMotivation, Long> {

    @Query("select um from user_motivation as um where um.user.id = :userId")
    List<UserMotivation> findUserMotivationByUserId(@Param("userId") Long userId);



}
