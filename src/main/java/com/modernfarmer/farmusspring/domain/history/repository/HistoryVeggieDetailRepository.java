package com.modernfarmer.farmusspring.domain.history.repository;

import com.modernfarmer.farmusspring.domain.history.document.HistoryVeggieDetail;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryVeggieDetailRepository extends MongoRepository<HistoryVeggieDetail, ObjectId> {
}
