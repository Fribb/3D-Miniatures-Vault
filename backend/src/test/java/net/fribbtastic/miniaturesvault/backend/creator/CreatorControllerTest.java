package net.fribbtastic.miniaturesvault.backend.creator;

import net.fribbtastic.miniaturesvault.backend.exceptions.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Frederic Eßer
 */
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(controllers = CreatorController.class)
class CreatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreatorServiceImpl service;

    private final String endpoint = "/api/v1/creator";

    private final List<Creator> creatorList = Arrays.asList(
            new Creator("Test Creator Name 01"),
            new Creator("Test Creator Name 02")
    );

    private final Creator creator = new Creator(UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1"), "Test Creator 01");

    /**
     * Test the MVC Creator Controller to return a list with 2 elements
     *
     * @throws Exception Thrown through the MockMVC Perform
     */
    @Test
    @DisplayName("Test: get all Creators [WebMVC]")
    public void testMvcGetAllCreators() throws Exception {

        Mockito.when(this.service.getAll()).thenReturn(this.creatorList);

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.endpoint).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("Test Creator Name 01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].name").value("Test Creator Name 02"));

        Mockito.verify(this.service, Mockito.times(1)).getAll();
    }

    /**
     * Test the MVC Creator Controller while returning an Empty list
     *
     * @throws Exception Thrown through the MockMVC Perform
     */
    @Test
    @DisplayName("Test: get all Creators with Empty List [WebMVC]")
    public void testMvcGetAllCreators_Empty() throws Exception {

        Mockito.when(this.service.getAll()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.endpoint).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());

        Mockito.verify(this.service, Mockito.times(1)).getAll();
    }

    /**
     * Test the MVC Creator Controller to return a single Creator requested by its ID
     *
     * @throws Exception Thrown through the MockMVC Perform
     */
    @Test
    @DisplayName("Test: get one Creator")
    public void testMvcGetOneCreators() throws Exception {

        Mockito.when(this.service.getOne(this.creator.getId())).thenReturn(this.creator);

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.endpoint + "/" + this.creator.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Test Creator 01"));

        Mockito.verify(this.service, Mockito.times(1)).getOne(this.creator.getId());
    }

    /**
     * Test the MVC Creator Controller while the Creator is not available
     *
     * @throws Exception Thrown through the MockMVC Perform
     */
    @Test
    @DisplayName("Test: get one missing Creator")
    public void testMvcGetOneCreator_Empty() throws Exception {

        UUID id = UUID.randomUUID();

        Mockito.when(this.service.getOne(id)).thenThrow(new ResourceNotFoundException(id));

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.endpoint + "/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> Assertions.assertThat(result.getResolvedException())
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Resource with the id '" + id + "' could not be found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.type").value(ResourceNotFoundException.class.getSimpleName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("Resource not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.details").value("Resource with the id '" + id + "' could not be found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.timestamp").isNotEmpty());

        Mockito.verify(this.service, Mockito.times(1)).getOne(id);

    }
}