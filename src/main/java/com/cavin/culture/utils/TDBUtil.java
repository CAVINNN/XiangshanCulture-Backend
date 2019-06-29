package com.cavin.culture.utils;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.tdb.TDBFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TDBUtil {

    public static String tdbDirectory;
    public static final String defaultNamedModel = "http://www.owl-ontologies.com/culture";
    public static final String inferredNamedModel = "http://www.owl-ontologies.com/cultureInferred";
    public static String owlPath;
    public static Set<String> urls;

    static {
        try {
            owlPath = URLDecoder.decode("file:" + TDBUtil.class.getClassLoader().getResource("static").getPath() + File.separator + "owl" + File.separator,"utf-8");
            tdbDirectory = URLDecoder.decode(TDBUtil.class.getClassLoader().getResource("static").getPath() + File.separator + "tdb","utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static Dataset getDataSet() {
        return TDBFactory.createDataset(tdbDirectory);
    }

    public static Model getDefaultModel() {
        Dataset ds = null;
        Model model = null;
        try {
            ds = TDBFactory.createDataset(tdbDirectory);
            ds.begin(ReadWrite.READ);
            model = ds.getNamedModel(TDBUtil.defaultNamedModel);
            ds.end();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
        return model;
    }

    public static Model getInferredModel() {
        Dataset ds = null;
        Model model = null;
        try {
            ds = TDBFactory.createDataset(tdbDirectory);
            ds.begin(ReadWrite.READ);
            model = ds.getNamedModel(TDBUtil.inferredNamedModel);
            ds.end();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
        return model;
    }

    public static OntModel getDefaultOntModel() {
        Dataset ds = null;
        OntModel ontModel = null;
        try {
            ds = TDBFactory.createDataset(tdbDirectory);
            ds.begin(ReadWrite.READ);
            ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, ds.getNamedModel(TDBUtil.defaultNamedModel));
            ds.end();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
        return ontModel;
    }

    public static OntModel getInferredOntModel() {
        Dataset ds = null;
        OntModel ontModel = null;
        try {
            ds = TDBFactory.createDataset(tdbDirectory);
            ds.begin(ReadWrite.READ);
            ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, ds.getNamedModel(TDBUtil.inferredNamedModel));
            ds.end();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
        return ontModel;
    }

    public static Set<String> getURIs() {
        Set<String> set = new HashSet<>();
        set.add("http://www.owl-ontologies.com/Individual.owl");
        set.add("http://www.owl-ontologies.com/Event.owl");
        set.add("http://www.owl-ontologies.com/Location.owl");
        set.add("http://www.owl-ontologies.com/Think.owl");
        set.add("http://www.owl-ontologies.com/Relics.owl");
        set.add("http://www.owl-ontologies.com/Localism.owl");
        set.add("http://www.owl-ontologies.com/Time.owl");
        set.add("http://www.owl-ontologies.com/Folk.owl");
        set.add("http://www.owl-ontologies.com/Organization.owl");
        set.add("http://www.owl-ontologies.com/Duty_Title.owl");
        set.add("http://www.owl-ontologies.com/Art.owl");
        return set;
    }

    // org.apache.jena.sparql.ARQException: ResultSet no longer valid (QueryExecution has been closed)
    public static ResultSet query(String queryString) {
        Dataset ds = null;
        Model model = null;
        ResultSet results = null;
        try {
            ds = TDBFactory.createDataset(tdbDirectory);
            ds.begin(ReadWrite.READ);
            model = ds.getNamedModel(inferredNamedModel);
            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            results = qe.execSelect();
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
        return results;
    }

    public static void close(Dataset dataset) {
        if (dataset != null) {
            dataset.close();
        }
    }

}
