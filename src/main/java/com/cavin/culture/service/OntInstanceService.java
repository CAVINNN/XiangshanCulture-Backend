package com.cavin.culture.service;

import com.cavin.culture.bean.Triple;

import java.util.List;
import java.util.Map;

public interface OntInstanceService {

    List<String> getInstancesByClass(String className);

    List<String> getAllObjectProperties();

    List<String> getInputSuggestion(String queryString);

    Map<String, String> getIndividualInfo(String individualName);

    Map<String, Object> getIndividualRlatOfType(String individualName);

    List<Triple> getIndividualRlat(String individualName);

    List<Map<String, String>> getResourceObjectWithCate(String individualName);

    List<Map<String, String>> queryForKnowledge(String individualName, String scope);

    Map<String, Object> queryForRelation(String individualName1, String individualName2);

    List<Triple> queryForProperty(String individualName, String predicateName);

}
