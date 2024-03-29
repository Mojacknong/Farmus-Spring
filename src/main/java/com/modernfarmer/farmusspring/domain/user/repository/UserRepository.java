package com.modernfarmer.farmusspring.domain.user.repository;


import com.modernfarmer.farmusspring.domain.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByUserNumber(String usernumber);

    @Query("SELECT a FROM user AS a  WHERE a.id = :userId")
    User findUserData(@Param("userId") Long userId);



}
