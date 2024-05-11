package com.modernfarmer.farmusspring.domain.history.document;

import com.modernfarmer.farmusspring.global.common.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document(collection = "history_detail")
public class HistoryVeggieDetail extends BaseDocument {

    @Id
    private ObjectId id;

    private List<HistoryPost> diaryPosts;
    private HistoryPost farmResult;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class HistoryPost {

        private String postImage;
        private String content;
        private String dateTime;
    }

    public static HistoryVeggieDetail createHistoryDetail(List<HistoryPost> diaryPosts, HistoryPost farmResult) {
        return HistoryVeggieDetail.builder()
                .diaryPosts(diaryPosts)
                .farmResult(farmResult)
                .build();
    }

    public void updateHistoryDetailResult(HistoryPost farmResult) {
        this.farmResult = farmResult;
    }
}
