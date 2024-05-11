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
@Document(collection = "history_club_detail")
public class HistoryFarmClubDetail extends BaseDocument {

    @Id
    private ObjectId id;

    private List<HistoryClubPost> missionPostList;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class HistoryClubPost {
        private int stepNum;
        private String stepName;
        private String postImage;
        private String content;
        private String date;
    }

    public static HistoryFarmClubDetail createHistoryClubDetail(List<HistoryClubPost> missionPostList) {
        return HistoryFarmClubDetail.builder()
                .missionPostList(missionPostList)
                .build();
    }
}
