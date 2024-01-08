package net.fribbtastic.miniaturesvault.backend.creator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Frederic Eßer
 */
@ExtendWith(MockitoExtension.class)
class CreatorServiceImplTest {

    @Mock
    private CreatorRepository repository;

    @InjectMocks
    private CreatorServiceImpl service;

    private final List<Creator> creatorList = Arrays.asList(
            new Creator(UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1"), "Test Creator 01"),
            new Creator(UUID.fromString("1eeaabc2-1093-4692-858b-e21cdee7ead6"), "Test Creator 02")
    );

    @BeforeEach
    void setUp() {
    }

    /**
     * Test the Service Layer for Creators to get All Creators
     */
    @Test
    @DisplayName("Test: get all Creators")
    public void testGetAllCreators() {

        Mockito.when(this.repository.findAll()).thenReturn(this.creatorList);

        List<Creator> creators = this.service.getAll();

        Assertions.assertThat(creators).isNotNull();
        Assertions.assertThat(creators.size()).isEqualTo(2);
        Assertions.assertThat(creators.get(0).getId().toString()).isEqualTo("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1");
        Assertions.assertThat(creators.get(0).getName()).isEqualTo("Test Creator 01");
        Assertions.assertThat(creators.get(1).getId().toString()).isEqualTo("1eeaabc2-1093-4692-858b-e21cdee7ead6");
        Assertions.assertThat(creators.get(1).getName()).isEqualTo("Test Creator 02");
    }

    @Test
    @DisplayName("Test: get all Creators (Empty list)")
    public void testGetAllCreatorsEmpty() {

        Mockito.when(this.repository.findAll()).thenReturn(Collections.emptyList());

        List<Creator> creators = this.service.getAll();

        Assertions.assertThat(creators).isNotNull();
        Assertions.assertThat(creators).isEmpty();
    }
}