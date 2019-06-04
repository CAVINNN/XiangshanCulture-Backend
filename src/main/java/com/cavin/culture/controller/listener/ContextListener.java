package com.cavin.culture.controller.listener;

import com.cavin.culture.utils.TDBUtil;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.tdb.TDBFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        File file = new File(TDBUtil.tdbDirectory);

        if ( !file.isDirectory() ) {

            Dataset dataset = null;
            OntModel ontModel = null;
            try {

                ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
                ontModel.read(TDBUtil.owlPath + "culture.owl");

                dataset = TDBUtil.getDataSet();
                dataset.begin(ReadWrite.WRITE) ;

                if( dataset.containsNamedModel(TDBUtil.defaultNamedModel) ) {
                    System.out.println("TDB中已有\"culture\" NamedGraph！");
                } else {
                    System.out.println("ontModelSize:" + ontModel.size());
                    dataset.addNamedModel(TDBUtil.defaultNamedModel, ontModel);
                }

                if( dataset.containsNamedModel(TDBUtil.inferredNamedModel) ) {
                    System.out.println("TDB中已有\"cultureInferred\" NamedGraph！");
                } else {
                    List<Rule> rules = null;
                    File rulesFile = null;
                    try {
                        rulesFile = new File(URLDecoder.decode(TDBUtil.class.getClassLoader().getResource("static").getPath() + File.separator + "rules" + File.separator + "xiangshan.rules","utf-8"));
                        Reader reader = new InputStreamReader(new FileInputStream(rulesFile), "UTF-8");
                        BufferedReader br = new BufferedReader(reader);
                        rules = Rule.parseRules(Rule.rulesParserFromReader(br));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Reasoner reasoner = new GenericRuleReasoner(rules);
                    InfModel inf = ModelFactory.createInfModel(reasoner, ontModel);
                    Model infModel = inf.getDeductionsModel();
                    Iterator<Statement> deduct = infModel.listStatements();
                    while (deduct.hasNext()) {
                        ontModel.add(deduct.next());
                    }
                    System.out.println("ontModelSize:" + ontModel.size());
                    dataset.addNamedModel(TDBUtil.inferredNamedModel, ontModel);
                }

                dataset.commit();
                dataset.end();
                System.out.println("------ 建立TDB完成 ------");
            } finally {
                if (ontModel != null) {
                    ontModel.close();
                }
                if (dataset != null) {
                    dataset.close();
                }
            }
        } else {
            System.out.println("------ 使用已创建的TDB ------");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        TDBUtil.close(TDBUtil.getDataSet());
    }

}
