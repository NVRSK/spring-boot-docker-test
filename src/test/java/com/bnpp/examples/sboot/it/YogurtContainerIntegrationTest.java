package com.bnpp.examples.sboot.it;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.FileSystems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@Testcontainers
@SpringBootTest
public class YogurtContainerIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(YogurtContainerIntegrationTest.class);

    public static int CONTAINER_PORT = 8080;

    /*
    *   TODO: check https://stackoverflow.com/questions/71854493/testcontainers-communication-between-containers-mapped-outside-port
    *   regarding communications between containers
    */

    @Test
    public void testYogurtContainer() throws Exception {
        GenericContainer yogurtContainer = new GenericContainer(new ImageFromDockerfile()
                .withDockerfile(FileSystems.getDefault().getPath(".", "Dockerfile")))
                .withExposedPorts(CONTAINER_PORT)
                .waitingFor(Wait.forListeningPort());   // tell's Testcontainers to wait with tests before the container starts to reply with excpected strategy

        log.info("============= 1 " + yogurtContainer.getLogs());
        try {
            yogurtContainer.start();
        } catch (Exception e)
        {
            throw e;
        } finally {
            log.info("============= 2 " + yogurtContainer.getLogs());
        }

        assertTrue(yogurtContainer.isRunning());

        String address = "http://"
                + yogurtContainer.getHost()
                + ":" + yogurtContainer.getMappedPort(CONTAINER_PORT) + "/yogurt";

        log.info("Constructed Yogurt's container URL: " + address);

        RestTemplate restTemplate = new RestTemplate();
        String reply = restTemplate.getForObject(address, String.class);
        log.info("Container reply: [" + reply + "]");
        assertThat(reply).contains("BNPP");

        yogurtContainer.stop();
    }
}
