package com.cavin.culture.controller;

import com.cavin.culture.bean.JsonMessage;
import com.cavin.culture.bean.Triple;
import com.cavin.culture.service.OntInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class OntInstanceController {

    @Autowired
    private OntInstanceService ontInstanceService;

    @RequestMapping(value = "/getInstancesByClass", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage getInstancesByClass(@RequestParam(value = "className")String className) {
        List<String> list = ontInstanceService.getInstancesByClass(className);
        return JsonMessage.success().addData("instances", list);
    }

    @RequestMapping(value = "/getObjectProperties", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage getObjectProperties() {
        List<String> list = ontInstanceService.getAllObjectProperties();
        return JsonMessage.success().addData("properties", list);
    }

    @RequestMapping(value = "/getInputSuggestion", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage getInputSuggestion(@RequestParam(value = "queryString")String queryString) {
        List<String> suggestion = ontInstanceService.getInputSuggestion(queryString);
        return JsonMessage.success().addData("suggestion", suggestion);
    }

    @RequestMapping(value = "/getRelationshipOfIns", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage getRelationshipOfIns(@RequestParam(value = "individualName")String individualName) {
        List<Map<String, String>> nodesList = ontInstanceService.getResourceObjectWithCate(individualName);
        List<Triple> linksList = ontInstanceService.getIndividualRlat(individualName);
        return JsonMessage.success().addData("nodes", nodesList).addData("links", linksList);
    }

    @RequestMapping(value = "/getIndividualInfo", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage getIndividualInfo(@RequestParam(value = "individualName")String individualName) {
        Map<String, String> infoMap = ontInstanceService.getIndividualInfo(individualName);
        return JsonMessage.success().addData("info", infoMap);
    }

    @RequestMapping(value = "/getIndividualRlatOfType", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage getIndividualRlatOfType(@RequestParam(value = "individualName")String individualName) {
        Map<String, Object> rlatMap = ontInstanceService.getIndividualRlatOfType(individualName);
        return JsonMessage.success().addData("rlat", rlatMap);
    }

    @RequestMapping(value = "/queryForKnowledge", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage queryForKnowledge(@RequestParam(value = "individualName")String individualName,
                                         @RequestParam(value = "scope")String scope) {
        List<Map<String, String>> nodes = ontInstanceService.getResourceObjectWithCate(individualName);
        List<Map<String, String>> queryResults = ontInstanceService.queryForKnowledge(individualName, scope);
        return JsonMessage.success().addData("nodes", nodes).addData("queryResults", queryResults);
    }

    @RequestMapping(value = "/queryForRelation", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage queryForRelation(@RequestParam(value = "individualName1")String individualName1,
                                        @RequestParam(value = "individualName2")String individualName2) {
        Map<String, Object> map = ontInstanceService.queryForRelation(individualName1, individualName2);
        return JsonMessage.success().addData("queryResults", map);
    }

    @RequestMapping(value = "/queryForProperty", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage queryForProperty(@RequestParam(value = "individualName")String individualName,
                                        @RequestParam(value = "predicateName")String predicateName) {
        List<Map<String, String>> nodesList = ontInstanceService.getResourceObjectWithCate(individualName);
        List<Triple> linksList = ontInstanceService.queryForProperty(individualName, predicateName);
        return JsonMessage.success().addData("nodes", nodesList).addData("queryResults", linksList);
    }

}
