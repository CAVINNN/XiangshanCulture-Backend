package com.cavin.culture.service;

import com.cavin.culture.bean.History;

import java.util.List;

public interface HistoryService {

    List<History> getHistoriesByNameAndType(String userName, String historyScope);

    Integer insertHistory(History history);

    Integer deleteHistoryById(Integer historyId);

}
