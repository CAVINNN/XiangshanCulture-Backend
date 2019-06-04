package com.cavin.culture.dao;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.StmtIterator;

import java.util.List;
import java.util.Map;

public interface OntInstanceDao {

    List<OntResource> getInstancesByClass(OntClass ontClass);

    StmtIterator getStatementsByInstance(Individual individual);

    List<Map<String, String>> queryForKnowledge(String individualURI, String scopeURI);

    List<String> queryForRelation(String individualURI1, String individualURI2);

    List<String> queryForProperty(String individualURI, String predicateURI);

}
