package com.cavin.culture.service;

import java.util.List;
import java.util.Map;

public interface OntClassService {

    List<Map<String, Object>> getRootClasses();

    List<Map<String, Object>> getSubClasses(String className);

    List<String> getSuperClasses(String className);

}
