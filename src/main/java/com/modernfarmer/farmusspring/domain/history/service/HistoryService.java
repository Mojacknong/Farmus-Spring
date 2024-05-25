package com.modernfarmer.farmusspring.domain.history.service;

import com.modernfarmer.farmusspring.domain.history.document.History;
import com.modernfarmer.farmusspring.domain.history.dto.res.HistoryResponseDto;
import com.modernfarmer.farmusspring.domain.history.helper.HistoryHelper;
import com.modernfarmer.farmusspring.domain.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryHelper historyHelper;

    public HistoryResponseDto getUserHistory(Long userId) {
        History history = historyHelper.getUserHistory(userId);
        return HistoryResponseDto.of(history);
    }
}
