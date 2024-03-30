package com.modernfarmer.farmusspring.domain.veggieinfo.repository;


import com.modernfarmer.farmusspring.domain.veggieinfo.dto.res.InfoForRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VeggieInfoRepositoryImpl implements CustomVeggieInfoRepository{

    private final MongoTemplate mongoTemplate;

    @Override
    public List<InfoForRegister> getVeggieInfoListForRegister() {
        // Query to get all name, difficulty, veggieImage, period from veggieInfo collection
        // and return as List<InfoForRegister>
        Query query = new Query();
        query.fields()
                .include("name")
                .include("difficulty")
                .include("veggieImage")
                .include("period");

        return mongoTemplate.find(query, InfoForRegister.class, "veggie_info");
    }
}
