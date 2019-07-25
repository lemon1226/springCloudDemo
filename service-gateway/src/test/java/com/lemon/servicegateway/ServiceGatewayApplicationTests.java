package com.lemon.servicegateway;

import com.lemon.baseutils.util.BCryptPasswordEncoderUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceGatewayApplicationTests {

    @Test
    public void contextLoads() {
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("lemon"));
        System.out.println(bCryptPasswordEncoder.upgradeEncoding("$2a$10$7G77FCCcgE/ug6iOPvGdHeViatwIzTJqPGLTzJexT567FDC9xj0R6"));
    }
}
