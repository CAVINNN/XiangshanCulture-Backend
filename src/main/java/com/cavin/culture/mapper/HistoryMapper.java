package com.cavin.culture.mapper;

import com.cavin.culture.bean.History;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface HistoryMapper {

    @Options(keyProperty = "history_id")
    @Insert("insert into history(history_subject, history_predicate, history_object, history_scope, history_type, create_date, user_name)" +
            " values(#{historySubject}, #{historyPredicate}, #{historyObject}, #{historyScope}, #{historyType}, #{createDate}, #{userName})")
    Integer insertHistory(History history);

    @Select("select * from history where user_name=#{userName} and history_type=#{historyType}")
    List<History> getHistoriesByNameAndType(@Param("userName") String userName, @Param("historyType") String historyType);

    @Delete("delete from history where history_id=#{historyId}")
    Integer deleteHistoryById(Integer historyId);

}
