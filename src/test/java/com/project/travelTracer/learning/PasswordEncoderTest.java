package com.project.travelTracer.learning;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    String password = "LeeYeChan";

    @Test
    public void encoding() throws Exception {
        String password = "LeeYeChan";
        String encodePassword = passwordEncoder.encode(password);

        assertThat(encodePassword).startsWith("{");
        assertThat(encodePassword).contains("{bcrypt}");
        assertThat(encodePassword).isNotEqualTo(password);
        assertThat(passwordEncoder.matches(password,encodePassword)).isTrue();
    }
}
