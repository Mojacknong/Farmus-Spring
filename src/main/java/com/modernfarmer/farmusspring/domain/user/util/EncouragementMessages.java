package com.modernfarmer.farmusspring.domain.user.util;


public enum EncouragementMessages {
    채소값절약(new String[]{
            "채소를 직접 길러_식비를 줄여보세요!",
            "직접 기른 채소로_알뜰하고 현명한 식사를 해봐요!",
            "오늘도 채솟값_알뜰하게 아끼는 중!",
            "홈파밍으로 지출은 줄이고_성취감은 더해봐요",
            "직접 기른 채소로_매일 알뜰하게, 매일 특별하게!"
    }),
    신선하고안전한식재료(new String[]{
            "파머님, 직접 기른 채소로_건강한 삶을 챙겨봐요!",
            "파머님의 사랑으로_오늘도 쑥쑥 자라는 중",
            "직접 기른 채소를_먹을 수 있는 그날까지!",
            "신선함으로 가득 채워지는 식탁, _ 늘 함께할게요!",
            "눈으로 보는 재미, 입으로 맛보는_홈파밍의 즐거움!"
    }),
    스트레스해소와정서적안정(new String[]{
            "바쁜 일상에서 잠깐 벗어나_텃밭을 가꿔봐요",
            "홈파밍을 하며_마음의 안정을 느껴봐요!",
            "스트레스 받는 하루, _ 홈파밍으로 힐링하세요",
            "나만의 채소를 키우며_소소한 성취감을 느껴보세요",
            "성취감은 꾸준함에서! _ 오늘도 함께 성장해요"
    });
    private final String[] messages;

    EncouragementMessages(String[] messages) {
        this.messages = messages;
    }

    public String getRandomMessage() {
        int index = (int) (Math.random() * messages.length);
        return messages[index];
    }

    public static String getRandomMessageByKey(EncouragementMessages key) {
        return key.getRandomMessage();
    }
}
