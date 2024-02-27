package org.monkey.oauth.common.utils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.Assert.*;

@SpringBootTest
public class JwtUtilTest {

    @Test
    public void getJwt() {
        HashMap<String, Object> claims = new HashMap<>();
        String token = JwtUtil.getJwt(claims);
        System.out.println(token);
    }

    @Test
    public void parseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTk5NzQ1MDd9.-fiRGWVvwCuCWCInY4F1RhaDkfZkJf7aVyYpioDhqMw";
        JwtUtil.parseJwt(token);
    }
}