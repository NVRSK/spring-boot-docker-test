package com.bnpp.examples.sboot;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(/* Random server port to avoid conflicts with running app */
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootTestApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(SpringBootTestApplicationTests.class);

    @LocalServerPort
    private int port;

    @Autowired
    private OController oController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(oController).isNotNull();
        String reply = this.restTemplate.getForObject("http://localhost:" + port + "/yogurt", String.class);
        log.info("Service reply: [" + reply + "]");
        assertThat(reply).contains("BNPP");
    }

}
