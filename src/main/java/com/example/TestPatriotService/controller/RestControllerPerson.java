package com.example.TestPatriotService.controller;

import com.example.TestPatriotService.Db.DbOperations;
import com.example.TestPatriotService.Db.Person;
import com.example.TestPatriotService.security.JWTService;
import com.example.TestPatriotService.security.SimpleAuth;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/persons")
public class RestControllerPerson {

    @GetMapping("/all")
    public Person[] getAllPersons(String token){
        if(SimpleAuth.checkToken(token)) return DbOperations.selectAll();
        return null;
    }
//curl -d "username=sf&password=pass" -X POST http://localhost:8080/persons/jwt_auth
    @PostMapping(path="/jwt_auth")
    public String getToken(@RequestParam String username, @RequestParam String password) {
        String token;
        System.out.println(username + " " + password);
        if(SimpleAuth.checkAuth(username, password)){
            token = JWTService.createJWT(username, "subject");
        }else token = null;
        return token;
    }

    @GetMapping("/by_sfid")
    public ArrayList<Person> getPersonBySfid(@RequestBody String sfid, String token) {
        if(SimpleAuth.checkToken(token))
            return DbOperations.selectPersonBySfid(sfid);
        return null;
    }

    @PostMapping("/create_person")
    public Boolean createPerson(@RequestBody Person newPerson, String token) {
        if(SimpleAuth.checkToken(token))
            return DbOperations.insertPerson( newPerson);
        return null;
    }

    @PostMapping("/create_persons")
    public Map<String,Boolean> createPersons(@RequestBody List<Person> newPersons, String token) {
        if(SimpleAuth.checkToken(token)){
            Map<String,Boolean> resultMap = new HashMap<String, Boolean>();
            for(Person p : newPersons) {
                resultMap.put(p.getSfId(), DbOperations.insertPerson(p));
            }
            return resultMap;
        }
        return null;
    }
}
