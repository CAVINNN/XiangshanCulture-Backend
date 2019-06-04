package com.cavin.culture.dao.impl;

import com.cavin.culture.dao.OntInstanceDao;
import com.cavin.culture.utils.TDBUtil;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OntInstanceDaoImpl implements OntInstanceDao {

    @Override
    public List<OntResource> getInstancesByClass(OntClass ontClass) {
        List<OntResource> resources = new ArrayList<>();
        ExtendedIterator<? extends OntResource> resourceIterator = ontClass.listInstances();
        while ( resourceIterator.hasNext() ) {
            OntResource resource = resourceIterator.next();
            if ( !resource.isAnon() ) {
                resources.add(resource);
            }
        }
        return resources;
    }

    @Override
    public StmtIterator getStatementsByInstance(Individual individual) {
        return individual.listProperties();
    }

    @Override
    public List<Map<String, String>> queryForKnowledge(String individualURI, String scopeURI) {
        Dataset ds = null;
        Model model = null;
        List<Map<String, String>> list = new ArrayList<>();
        String individualName = individualURI.substring(individualURI.indexOf("#") + 1);
        try {
            ds = TDBFactory.createDataset(TDBUtil.tdbDirectory);
            ds.begin(ReadWrite.READ);
            model = ds.getNamedModel(TDBUtil.inferredNamedModel);
            String queryString;
            if (scopeURI.equals("所有类")) {
                queryString = "SELECT ?p ?o WHERE { <" + individualURI + "> ?p ?o }";
            } else {
                queryString = "SELECT ?p ?o WHERE { <" + individualURI + "> ?p ?o FILTER regex(str(?o), '" + scopeURI + "') }";
            }
            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();
            while( results.hasNext() ){
                QuerySolution temp = results.nextSolution();
                RDFNode predicateNode = temp.get("p");
                RDFNode objectNode = temp.get("o");
                if ( objectNode.isResource() ) {
                    if (predicateNode.asResource().getLocalName().equals("type")) {
                        continue;
                    }
                    Map<String, String> map = new HashMap<>();
                    map.put("subject", individualName);
                    map.put("predicate", predicateNode.asResource().getLocalName());
                    map.put("object", objectNode.asResource().getLocalName());
                    StmtIterator iterator = objectNode.asResource().listProperties();
                    while (iterator.hasNext()) {
                        Statement statement = iterator.nextStatement();
                        if (statement.getPredicate().getLocalName().equals("comment")) {
                            map.put("objComment", statement.getObject().asLiteral().getString());
                        }
                    }
                    list.add(map);
                }
            }
            qe.close();
            ds.end();
        } finally {
            if (model != null) {
                model.close();
            }
            if (ds != null) {
                ds.close();
            }
        }
        return list;
    }

    @Override
    public List<String> queryForRelation(String individualURI1, String individualURI2) {
        Dataset ds = null;
        Model model = null;
        List<String> list = new ArrayList<>();
        try {
            ds = TDBFactory.createDataset(TDBUtil.tdbDirectory);
            ds.begin(ReadWrite.READ);
            model = ds.getNamedModel(TDBUtil.inferredNamedModel);
            String queryString = "SELECT ?p WHERE { <" + individualURI1 + "> ?p <" + individualURI2 + "> }";
            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();
            while( results.hasNext() ){
                QuerySolution temp = results.nextSolution();
                RDFNode predicateNode = temp.get("p");
                list.add(predicateNode.asResource().getLocalName());
            }
            qe.close();
            ds.end();
        } finally {
            if (model != null) {
                model.close();
            }
            if (ds != null) {
                ds.close();
            }
        }
        return list;
    }


    @Override
    public List<String> queryForProperty(String individualURI, String predicateURI) {
        Dataset ds = null;
        Model model = null;
        List<String> list = new ArrayList<>();
        try {
            ds = TDBFactory.createDataset(TDBUtil.tdbDirectory);
            ds.begin(ReadWrite.READ);
            model = ds.getNamedModel(TDBUtil.inferredNamedModel);
            String queryString = "SELECT ?o WHERE { <" + individualURI + "> <" + predicateURI + "> ?o }";
            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();
            while( results.hasNext() ){
                QuerySolution temp = results.nextSolution();
                RDFNode objectNode = temp.get("o");
                if ( objectNode.isResource() ) {
                    list.add(objectNode.asResource().getLocalName());
                }
            }
            qe.close();
            ds.end();
        } finally {
            if (model != null) {
                model.close();
            }
            if (ds != null) {
                ds.close();
            }
        }
        return list;
    }

}
