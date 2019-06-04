package com.cavin.culture;

import com.cavin.culture.bean.History;
import com.cavin.culture.bean.User;
import com.cavin.culture.mapper.HistoryMapper;
import com.cavin.culture.mapper.UserMapper;
import com.cavin.culture.utils.JWTUtil;
import com.cavin.culture.utils.SHAUtil;
import com.cavin.culture.utils.TDBUtil;
import org.apache.jena.ontology.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.MessageDigest;
import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Claims;
import java.security.Key;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CultureApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HistoryMapper historyMapper;

    @Test
    public void contextLoads() {

    }

    @Test
    public void TestHistory() {

        History history = new History(null, "孙中山", null, "宋庆龄", null, "relation", new Date(), "CAVINNN");
        int i = historyMapper.insertHistory(history);
        System.out.println(i);

//        List<History> histories = historyMapper.getHistoriesByNameAndType("CAVINNN", "attribute");
//        for (History history : histories) {
//            System.out.println(history);
//        }

    }

    @Test
    public void TestJWTUtil() {
        String token = JWTUtil.getJwtToken("Ethel");
        System.out.println(token);
        Claims claims = JWTUtil.parseToken(token);

        System.out.println();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuer());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getAudience());
    }

    @Test
    public void TestSHA256() {
        String sha256 = SHAUtil.getSHA256("hxy970317");
        System.out.println(sha256);
    }

    @Test
    public void TestUser() {
        User user = userMapper.getUserByName("Ethel");
        System.out.println(user.getUserName());

//        Date date = new Date();
//
//        String sha256 = SHAUtil.getSHA256("HJWyoung143567aw");
//
//        User user = new User("Ethel", sha256, "hxy@126.com", date);
//        int i = userMapper.insertUser(user);
//        System.out.println(i);
    }

    @Test
    public void TestJWT() {
        // JWT的生成时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // JWT的id
        String timestampId = String.valueOf(now.getTime()) + "Young";

        // JWT的加密方式
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // 自定义声明
        Map<String, Object> map = new HashMap<>();
        map.put("userid", "3");
        map.put("username", "CAVINNN");

        // 生成JWT
        JwtBuilder builder = Jwts.builder().setId(timestampId)
                .setIssuedAt(now)
                .setSubject("UserInfo")
                .setIssuer("Cavin")
                .signWith(key);

        String jws = builder.compact();

        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody();

        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuer());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.get("username"));
    }

    public void testOntModelFromTdb() {

        Dataset ds = null;
        Model model = null;
        OntModel ontModel = null;

        try {
            ds = TDBFactory.createDataset(TDBUtil.tdbDirectory);

            ds.begin(ReadWrite.READ);

            model = ds.getNamedModel(TDBUtil.inferredNamedModel);
            ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);

            System.out.println( ontModel.size() );

            ds.end();
        } finally {
            if (ontModel != null) {
                ontModel.close();
            }
            if (model != null) {
                model.close();
            }
            if (ds != null) {
                ds.close();
            }
        }

    }

    public void testOneOWL() {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.read("G:/culture.owl");
        Iterator<OntClass> classIterator = ontModel.listHierarchyRootClasses();
        while ( classIterator.hasNext() ) {
            OntClass ontClass = classIterator.next();
            if ( !ontClass.isAnon() ) {
                System.out.println( ontClass.getURI().substring(0, ontClass.getURI().indexOf("#")) );
            }
        }
    }

    public void testKnowLedgeSparql() {
        Dataset ds = null;
        Model model = null;

        try {
            ds = TDBFactory.createDataset(TDBUtil.tdbDirectory);
            ds.begin(ReadWrite.READ);

            model = ds.getNamedModel(TDBUtil.inferredNamedModel);
            String queryString = "SELECT ?p ?o WHERE { <http://www.owl-ontologies.com/Individual.owl#孙中山> ?p ?o FILTER regex(str(?o), 'http://www.owl-ontologies.com/Individual.owl') }";

            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();
//            ResultSetFormatter.out(System.out, results, query);

            while( results.hasNext() ){
                QuerySolution temp = results.nextSolution();
                RDFNode predicateNode = temp.get("p");
                RDFNode objectNode = temp.get("o");
                if ( objectNode.isResource() ) {
                    System.out.println(predicateNode.asResource().getLocalName());
                    System.out.println(objectNode.asResource().getLocalName());
//                    StmtIterator iterator = objectNode.asResource().listProperties();
//                    while (iterator.hasNext()) {
//                        Statement statement = iterator.nextStatement();
//                        if (statement.getPredicate().getLocalName().equals("comment")) {
//                            System.out.println(statement);
//                        }
//                    }
                }
                System.out.println();
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
    }

    @Test
    public void testRelationSparql() {
        Dataset ds = null;
        Model model = null;
        try {
            ds = TDBFactory.createDataset(TDBUtil.tdbDirectory);
            ds.begin(ReadWrite.READ);

            model = ds.getNamedModel(TDBUtil.inferredNamedModel);
            String queryString = "SELECT ?p WHERE { <http://www.owl-ontologies.com/Individual.owl#孙中山> ?p <http://www.owl-ontologies.com/Individual.owl#容闳> }";

            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();
//            ResultSetFormatter.out(System.out, results, query);

            while( results.hasNext() ){
                QuerySolution temp = results.nextSolution();
                RDFNode predicateNode = temp.get("p");
                System.out.println(predicateNode.asResource().getLocalName());
            }

//            while( results.hasNext() ){
//                QuerySolution temp = results.nextSolution();
//                RDFNode predicateNode = temp.get("p");
//                RDFNode objectNode = temp.get("o");
//                if ( objectNode.isResource() ) {
//                    System.out.println(predicateNode.asResource().getLocalName());
//                    System.out.println(objectNode.asResource().getLocalName());
//                    StmtIterator iterator = objectNode.asResource().listProperties();
//                    while (iterator.hasNext()) {
//                        Statement statement = iterator.nextStatement();
//                        if (statement.getPredicate().getLocalName().equals("comment")) {
//                            System.out.println(statement);
//                        }
//                    }
//                }
//            }

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
    }

    public void testAttrSparql() {
        Dataset ds = null;
        Model model = null;
        try {
            ds = TDBFactory.createDataset(TDBUtil.tdbDirectory);
            ds.begin(ReadWrite.READ);

            model = ds.getNamedModel(TDBUtil.inferredNamedModel);
            String queryString = "SELECT ?o WHERE { <http://www.owl-ontologies.com/Individual.owl#孙中山> <http://www.owl-ontologies.com/Individual.owl#妻子是> ?o }";

            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();

            while( results.hasNext() ){
                QuerySolution temp = results.nextSolution();
                RDFNode objectNode = temp.get("o");
                if ( objectNode.isResource() ) {
                    System.out.println(objectNode.asResource().getLocalName());
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
    }

    public void getObjectProperties() {
        OntModel ontModel = null;
        try {
            ontModel = TDBUtil.getInferredOntModel();
            ExtendedIterator<ObjectProperty> properties = ontModel.listObjectProperties();

            int i = 0;
            while (properties.hasNext()) {
                OntProperty ontProperty = properties.next();
                if ( !ontProperty.isAnon() ) {
                    System.out.println(ontProperty.asResource().getURI());
                }
            }
        } finally {
            if (ontModel != null) {
                ontModel.close();
            }
        }
    }


    public void testSuggestion() {
        String queryString = "孙中山";
        List<String> list = new ArrayList<>();

        ExtendedIterator<Individual> individualsIt = TDBUtil.getInferredOntModel().listIndividuals();
        while (individualsIt.hasNext()) {
            Individual individual = individualsIt.next();
            if (individual.asResource().getLocalName().contains(queryString)) {
                list.add(individual.asResource().getLocalName());
            }
        }

        for (String suggest : list) {
            System.out.println(suggest);
        }
    }

}

