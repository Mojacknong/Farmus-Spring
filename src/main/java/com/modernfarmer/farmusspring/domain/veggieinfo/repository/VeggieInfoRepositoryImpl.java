package com.modernfarmer.farmusspring.domain.veggieinfo.repository;


import com.modernfarmer.farmusspring.domain.veggieinfo.vo.CreateFarmClubVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.InfoForRegisterVo;
import com.modernfarmer.farmusspring.domain.veggieinfo.vo.StepVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VeggieInfoRepositoryImpl implements CustomVeggieInfoRepository{

    private final MongoTemplate mongoTemplate;

    @Override
    public List<InfoForRegisterVo> getVeggieInfoListForRegister() {
        // Query to get all name, difficulty, veggieImage, period from veggieInfo collection
        // and return as List<InfoForRegister>
        Query query = new Query();
        query.fields()
                .include("_id")
                .include("name")
                .include("difficulty")
                .include("veggieImage")
                .include("period");

        return mongoTemplate.find(query, InfoForRegisterVo.class, "veggie_info");
    }

    @Override
    public CreateFarmClubVo getVeggieInfoForCreateFarmClub(String veggieInfoId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(veggieInfoId))
                .fields()
                .include("_id")
                .include("name")
                .include("veggieImage")
                .include("difficulty");

        return mongoTemplate.findOne(query, CreateFarmClubVo.class, "veggie_info");
    }

    @Override
    public List<StepVo> getVeggieInfoStepList(String veggieInfoId) {
        MatchOperation match = Aggregation.match(Criteria.where("_id").is(veggieInfoId));
        UnwindOperation unwind = Aggregation.unwind("steps");
        ProjectionOperation project = Aggregation.project()
                .and("steps.num").as("num")
                .and("steps.content").as("content")
                .and("steps.tips").as("tips");
        Aggregation aggregation = Aggregation.newAggregation(match, unwind, project);
        AggregationResults<StepVo> results = mongoTemplate.aggregate(aggregation, "veggie_info", StepVo.class);

        return results.getMappedResults();
    }
}
