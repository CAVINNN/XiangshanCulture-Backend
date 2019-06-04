package com.cavin.culture.dao;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;

import java.util.List;

public interface OntClassDao {

    List<OntClass> getClasses();

    List<OntClass> getRootClasses();

    List<OntClass> getSuperClasses(OntClass ontClass);

    List<OntClass> getSubClasses(OntClass ontClass);

}
