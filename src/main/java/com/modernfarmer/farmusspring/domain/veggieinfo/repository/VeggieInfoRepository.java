package com.modernfarmer.farmusspring.domain.veggieinfo.repository;

import com.modernfarmer.farmusspring.domain.veggieinfo.entity.VeggieInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VeggieInfoRepository extends MongoRepository<VeggieInfo, ObjectId>, CustomVeggieInfoRepository {


}
