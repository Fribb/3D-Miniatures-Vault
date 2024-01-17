package net.fribbtastic.miniaturesvault.backend.creator;

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

    private final List<Creator> creatorList = Arrays.asList(
            new Creator("Test Creator Name 01"),
            new Creator("Test Creator Name 02")
    );

    private final String endpoint = "/api/v1/creator";

    /**
     * Test the MVC Controller to return a list with 2 elements
     *
     * @throws Exception Thrown through the MockMVC Perform
     */
    @Test
    @DisplayName("Test: get all Creators [WebMVC]")
    void testMvcGetAll() throws Exception {

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
     * Test the MVC Controller while returning an Empty list
     *
     * @throws Exception Thrown through the MockMVC Perform
     */
    @Test
    @DisplayName("Test: get all Creators with Empty List [WebMVC]")
    void testMvcGetAll_Empty() throws Exception {

        Mockito.when(this.service.getAll()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.endpoint).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());

        Mockito.verify(this.service, Mockito.times(1)).getAll();
    }
}