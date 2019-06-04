package com.cavin.culture.service.impl;

import com.cavin.culture.bean.History;
import com.cavin.culture.mapper.HistoryMapper;
import com.cavin.culture.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public List<History> getHistoriesByNameAndType(String userName, String historyType) {
        return historyMapper.getHistoriesByNameAndType(userName, historyType);
    }

    @Override
    public Integer insertHistory(History history) {
        return historyMapper.insertHistory(history);
    }

    @Override
    public Integer deleteHistoryById(Integer historyId) {
        return historyMapper.deleteHistoryById(historyId);
    }

}
