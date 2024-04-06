package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyVeggieRepository extends JpaRepository<MyVeggie, Long> {

    Optional<MyVeggie> findById(Long id);


    @Query("SELECT mv FROM my_veggie AS mv JOIN FETCH  mv.user WHERE mv.user.id = :userId")
    List<MyVeggie> findMyVeggieUserId(@Param("userId") Long userId);







}
