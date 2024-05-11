package com.modernfarmer.farmusspring.domain.history.repository;

import com.modernfarmer.farmusspring.domain.history.document.History;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<History, ObjectId>, CustomHistoryRepository {
}
