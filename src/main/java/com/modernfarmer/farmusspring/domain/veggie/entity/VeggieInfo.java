package com.modernfarmer.farmusspring.domain.veggie.entity;

import com.modernfarmer.farmusspring.global.common.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document(collection = "veggie_info")
public class VeggieInfo extends BaseDocument {

    @Id
    private ObjectId id;

    private String name;

    private String difficulty;

    private List<Step> steps;

    private String veggieImage;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Step {

        @Indexed(direction = IndexDirection.ASCENDING)
        private int num;
        private String content;
        private List<String> tips;
    }

    public static VeggieInfo createVeggieInfo(String name, String difficulty, List<Step> steps, String veggieImage) {
        return VeggieInfo.builder()
                .name(name)
                .difficulty(difficulty)
                .steps(steps)
                .veggieImage(veggieImage)
                .build();
    }
}


