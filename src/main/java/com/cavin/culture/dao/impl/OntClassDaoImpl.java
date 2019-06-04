package com.cavin.culture.dao.impl;

import com.cavin.culture.dao.OntClassDao;
import com.cavin.culture.utils.TDBUtil;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.query.Dataset;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class OntClassDaoImpl implements OntClassDao {

    @Override
    public List<OntClass> getClasses() {
        List<OntClass> list = new ArrayList<>();
        Iterator<OntClass> classIterator = TDBUtil.getInferredOntModel().listClasses();
        while ( classIterator.hasNext() ) {
            OntClass ontClass = classIterator.next();
            if ( !ontClass.isAnon() ) {
                list.add(ontClass);
            }
        }
        return list;
    }

    @Override
    public List<OntClass> getRootClasses() {
        List<OntClass> list = new ArrayList<>();
        Iterator<OntClass> classIterator = TDBUtil.getInferredOntModel().listHierarchyRootClasses();
        while ( classIterator.hasNext() ) {
            OntClass ontClass = classIterator.next();
            if ( !ontClass.isAnon() ) {
                list.add(ontClass);
            }
        }
        return list;
    }

    @Override
    public List<OntClass> getSuperClasses(OntClass inClass) {
        List<OntClass> list = new ArrayList<>();
        Iterator<OntClass> classIterator = inClass.listSuperClasses();
        while ( classIterator.hasNext() ) {
            OntClass ontClass = classIterator.next();
            if ( !ontClass.isAnon() ) {
                list.add(ontClass);
            }
        }
        return list;
    }

    @Override
    public List<OntClass> getSubClasses(OntClass inClass) {
        List<OntClass> list = new ArrayList<>();
        Iterator<OntClass> classIterator = inClass.listSubClasses();
        while ( classIterator.hasNext() ) {
            OntClass ontClass = classIterator.next();
            if ( !ontClass.isAnon() ) {
                list.add(ontClass);
            }
        }
        return list;
    }

}
