package com.modernfarmer.farmusspring.domain.history.repository;

import com.modernfarmer.farmusspring.domain.history.document.HistoryFarmClubDetail;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryFarmClubDetailRepository extends MongoRepository<HistoryFarmClubDetail, ObjectId> {
}
