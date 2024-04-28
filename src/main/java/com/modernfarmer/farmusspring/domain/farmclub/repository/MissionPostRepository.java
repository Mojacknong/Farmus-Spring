package com.modernfarmer.farmusspring.domain.farmclub.repository;

import com.modernfarmer.farmusspring.domain.farmclub.entity.MissionPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionPostRepository extends JpaRepository<MissionPost, Long>, MissionPostRepositoryCustom {
}
