package org.example.logintest.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode() {
        String password = "1234";

        String enPassword = passwordEncoder.encode(password);

        System.out.println("-------------- 암호화된 패스워드: " + enPassword);

        // 일치하면 true 반환
        boolean matchResult = passwordEncoder.matches(password, enPassword);

        System.out.println("-------------- 패스워드 일치여부: " + matchResult);
    }
}
