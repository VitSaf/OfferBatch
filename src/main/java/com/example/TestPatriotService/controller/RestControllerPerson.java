package com.example.TestPatriotService.controller;

import com.example.TestPatriotService.Db.DbOperations;
import com.example.TestPatriotService.Db.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestControllerPerson {

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Person[] getAllPersons(){
        return DbOperations.selectAll();
    }

    @RequestMapping(value = "/by_sfid", method = RequestMethod.GET)
    public ArrayList<Person> getPersonBySfid(String sfid) {
        return DbOperations.selectPersonBySfid(sfid);

    }

    @RequestMapping(value = "/create_person", method = RequestMethod.POST)
    public Boolean createPerson(@RequestBody Person newPerson) {
        return DbOperations.insert( newPerson);
    }

    @RequestMapping(value = "/create_persons", method = RequestMethod.POST)
    public Map<String,Boolean> createPersons(@RequestBody List<Person> newPersons) {
        Map<String,Boolean> resultMap = new HashMap<String, Boolean>();
        for(Person p : newPersons) {
            resultMap.put(p.getSfId(), DbOperations.insert(p));
        }
        return resultMap;
    }
}
