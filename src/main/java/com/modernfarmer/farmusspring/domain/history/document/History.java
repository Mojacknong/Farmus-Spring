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

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document(collection = "history")
public class History extends BaseDocument {

    @Id
    private ObjectId id;

    private Long userId;
    
    private List<Icon> veggieHistoryIcons;
    private List<Icon> farmClubHistoryIcons;

    private List<Detail> veggieHistoryDetails;
    private List<Detail> farmClubHistoryDetails;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Icon {
        private String url;
        private String backgroundColor;

        public static Icon createIcon(String url, String backgroundColor) {
            return Icon.builder()
                    .url(url)
                    .backgroundColor(backgroundColor)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Detail {

        private String detailId;
        private String image;
        private String historyName;
        private String name;
        private String period;

        public static Detail createDetail(String detailId, String image, String historyName, String name, String period) {
            return Detail.builder()
                    .detailId(detailId)
                    .image(image)
                    .historyName(historyName)
                    .name(name)
                    .period(period)
                    .build();
        }
    }

    public static History createHistory(Long userId) {
        return History.builder()
                .userId(userId)
                .veggieHistoryDetails(new ArrayList<>())
                .farmClubHistoryDetails(new ArrayList<>())
                .veggieHistoryIcons(new ArrayList<>())
                .farmClubHistoryIcons(new ArrayList<>())
                .build();
    }

    public void addVeggieHistoryDetail(Detail detail) {
        veggieHistoryDetails.add(detail);
    }

    public void addFarmClubHistoryDetail(Detail detail) {
        farmClubHistoryDetails.add(detail);
    }

    public void addVeggieHistoryIcon(Icon icon) {
        veggieHistoryIcons.add(icon);
    }

    public void addFarmClubHistoryIcon(Icon icon) {
        farmClubHistoryIcons.add(icon);
    }
}
