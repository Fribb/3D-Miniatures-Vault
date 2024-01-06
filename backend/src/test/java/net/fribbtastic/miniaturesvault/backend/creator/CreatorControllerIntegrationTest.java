package net.fribbtastic.miniaturesvault.backend.creator;

import net.fribbtastic.miniaturesvault.backend.Application;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Frederic Eßer
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreatorControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    private String url;

    @BeforeEach
    public void setUp() {
        this.url = "http://localhost:" + this.port + "/creator";
    }

    /**
     * Integration Test for the Creator endpoint
     * seed the DB with existing Data and get all creators
     */
    @Test
    @DisplayName("Test: get All creators [Integration]")
    @Sql({"classpath:creator/truncate.sql"})
    @Sql({"classpath:creator/insert.sql"})
    public void testIntegrationGetAll() {

        ResponseEntity<Creator[]> response = this.template.getForEntity(this.url, Creator[].class);

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody().length).isEqualTo(3);
        Assertions.assertThat(response.getBody()[0].getName()).isEqualTo("Test Creator Name 01");
        Assertions.assertThat(response.getBody()[1].getName()).isEqualTo("Test Creator Name 02");
        Assertions.assertThat(response.getBody()[2].getName()).isEqualTo("Test Creator Name 03");
    }

    @Test
    @DisplayName("Test: get all creators with empty list [Integration]")
    @Sql({"classpath:creator/truncate.sql"})
    public void testIntegrationGetAll_Empty() {

        ResponseEntity<Creator[]> response = this.template.getForEntity(this.url, Creator[].class);

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody().length).isEqualTo(0);
    }
}