package com.cavin.culture.service.impl;

import com.cavin.culture.dao.OntClassDao;
import com.cavin.culture.service.OntClassService;
import com.cavin.culture.utils.TDBUtil;
import org.apache.jena.ontology.OntClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OntClassServiceImpl implements OntClassService {

    @Autowired
    private OntClassDao ontClassDao;

    @Override
    public List<Map<String, Object>> getRootClasses() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OntClass rootClass : ontClassDao.getRootClasses()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", rootClass.getLocalName());
            map.put("hasSubClasses", rootClass.hasSubClass());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getSubClasses(String className) {
        OntClass inClass = null;
        Set<String> urls = TDBUtil.getURIs();
        for (String url : urls) {
            inClass = TDBUtil.getInferredOntModel().getOntClass(url + "#" + className);
            if (inClass != null) {
                break;
            }
        }
        if (inClass != null) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (OntClass clz : ontClassDao.getSubClasses(inClass)) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", clz.getLocalName());
                map.put("hasSubClasses", clz.hasSubClass());
                list.add(map);
            }
            return list;
        } else {
            // error
            System.out.println("未找到OntClass");
            return null;
        }
    }

    @Override
    public List<String> getSuperClasses(String className) {
        OntClass inClass = null;
        Set<String> urls = TDBUtil.getURIs();
        for (String url : urls) {
            inClass = TDBUtil.getInferredOntModel().getOntClass(url + "#" + className);
            if (inClass != null) {
                break;
            }
        }
        if (inClass != null) {
            List<String> list = new ArrayList<>();
            for (OntClass clz : ontClassDao.getSuperClasses(inClass)) {
                list.add(clz.getLocalName());
            }
            return list;
        } else {
            // error
            System.out.println("未找到OntClass");
            return null;
        }
    }

}
