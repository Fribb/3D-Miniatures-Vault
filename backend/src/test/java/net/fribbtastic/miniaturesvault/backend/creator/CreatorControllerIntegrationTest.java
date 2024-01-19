package net.fribbtastic.miniaturesvault.backend.creator;

import net.fribbtastic.miniaturesvault.backend.Application;
import net.fribbtastic.miniaturesvault.backend.exceptions.ResourceNotFoundException;
import net.fribbtastic.miniaturesvault.backend.response.ApiResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

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
        this.url = "http://localhost:" + this.port + "/api/v1/creator";
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

        ResponseEntity<ApiResponse<List<Creator>>> response = this.template.exchange(this.url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(200);
        Assertions.assertThat(response.getBody().getData()).isNotNull();
        Assertions.assertThat(response.getBody().getData().size()).isEqualTo(3);

        Assertions.assertThat(response.getBody().getData().get(0).getId()).isEqualTo(UUID.fromString("0320a817-a06b-48d8-8d36-a55a95650a10"));
        Assertions.assertThat(response.getBody().getData().get(0).getName()).isEqualTo("Test Creator Name 01");

        Assertions.assertThat(response.getBody().getData().get(1).getId()).isEqualTo(UUID.fromString("596202da-948a-4d9d-bb87-0bae675f7336"));
        Assertions.assertThat(response.getBody().getData().get(1).getName()).isEqualTo("Test Creator Name 02");

        Assertions.assertThat(response.getBody().getData().get(2).getId()).isEqualTo(UUID.fromString("6e169bf3-ac69-49b9-8e9c-38439b45e9bd"));
        Assertions.assertThat(response.getBody().getData().get(2).getName()).isEqualTo("Test Creator Name 03");
    }

    /**
     * Integration Test for the Creator endpoint,
     * the response should be an empty list of data
     */
    @Test
    @DisplayName("Test: get all creators with empty list [Integration]")
    @Sql({"classpath:creator/truncate.sql"})
    public void testIntegrationGetAll_Empty() {

        ResponseEntity<ApiResponse<List<Creator>>> response = this.template.exchange(this.url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(200);
        Assertions.assertThat(response.getBody().getData()).isNotNull();
        Assertions.assertThat(response.getBody().getData().size()).isEqualTo(0);
    }

    /**
     * Integration Test for the Creator Endpoints
     */
    @Test
    @DisplayName("Test: get one creator [Integration]")
    @Sql({"classpath:creator/truncate.sql"})
    @Sql({"classpath:creator/insert.sql"})
    public void testIntegrationGetOneCreator() {

        UUID id = UUID.fromString("0320a817-a06b-48d8-8d36-a55a95650a10");

        ResponseEntity<ApiResponse<Creator>> response = this.template.exchange(this.url + "/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(200);
        Assertions.assertThat(response.getBody().getData()).isNotNull();
        Assertions.assertThat(response.getBody().getData().getName()).isEqualTo("Test Creator Name 01");
    }

    @Test
    @DisplayName("Test: get one missing creator [Integration]")
    @Sql({"classpath:creator/truncate.sql"})
    public void testIntegrationGetOneCreator_Empty() {

        UUID id = UUID.fromString("0320a817-a06b-48d8-8d36-a55a95650a10");

        ResponseEntity<ApiResponse<Creator>> response = this.template.exchange(this.url + "/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(404);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getStatusCode()).isEqualTo(404);
        Assertions.assertThat(response.getBody().getErrorDetails()).isNotNull();
        Assertions.assertThat(response.getBody().getErrorDetails().getType()).isEqualTo(ResourceNotFoundException.class.getSimpleName());
        Assertions.assertThat(response.getBody().getErrorDetails().getMessage()).isEqualTo("Resource not found");
        Assertions.assertThat(response.getBody().getErrorDetails().getDetails()).isEqualTo("Resource with the id '" + id + "' could not be found");
        Assertions.assertThat(response.getBody().getErrorDetails().getTimestamp()).isNotEmpty();
    }
}