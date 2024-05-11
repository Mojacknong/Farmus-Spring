package com.modernfarmer.farmusspring.domain.history.repository;

import com.modernfarmer.farmusspring.domain.history.document.History;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HistoryRepository extends MongoRepository<History, ObjectId>, CustomHistoryRepository {

    Optional<History> findByUserId(Long userId);
}
