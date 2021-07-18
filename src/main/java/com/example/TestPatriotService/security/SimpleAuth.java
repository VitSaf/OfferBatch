package com.example.TestPatriotService.security;

import com.example.TestPatriotService.Db.DbOperations;
import com.example.TestPatriotService.Db.Person;
import com.example.TestPatriotService.Db.User;
import io.jsonwebtoken.Claims;

import java.util.Date;

public class SimpleAuth {
    private SimpleAuth(){};

    public static Boolean checkAuth(String username, String password) {
        User userAcc = DbOperations.selectUserByLogin(username);
        System.out.println(userAcc);
        return userAcc.getPassword().equals(password);
    }

    public static Boolean checkToken(String token) {
        Claims claims;
        try {
            claims = JWTService.decodeJWT(token);
        }catch (NullPointerException npe){
            return false;
        }
        return claims.getExpiration().getTime() - new Date().getTime() > 0 && DbOperations.selectUserByLogin(claims.getIssuer()) != null;
    }
}
