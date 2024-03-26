package com.modernfarmer.farmusspring;

import com.modernfarmer.farmusspring.global.common.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FarmusSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmusSpringApplication.class, args);
    }

}
