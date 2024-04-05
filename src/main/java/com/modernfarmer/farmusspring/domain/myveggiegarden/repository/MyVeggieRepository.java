package com.modernfarmer.farmusspring.domain.myveggiegarden.repository;


import com.modernfarmer.farmusspring.domain.myveggiegarden.entity.MyVeggie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyVeggieRepository extends JpaRepository<MyVeggie, Long> {

}
