package net.fribbtastic.miniaturesvault.backend.creator;

import net.fribbtastic.miniaturesvault.backend.exceptions.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

/**
 * @author Frederic Eßer
 */
@ExtendWith(MockitoExtension.class)
class CreatorServiceImplTest {

    @Mock
    private CreatorRepository repository;

    @InjectMocks
    private CreatorServiceImpl service;

    private final List<Creator> testCreatorList = Arrays.asList(
            new Creator(UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1"), "Test Creator 01"),
            new Creator(UUID.fromString("1eeaabc2-1093-4692-858b-e21cdee7ead6"), "Test Creator 02")
    );

    private final Creator testCreator = new Creator(UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1"), "Test Creator 01");

    @BeforeEach
    void setUp() {
    }

    /**
     * Test the Service Layer of the Creator to get All Creators
     */
    @Test
    @DisplayName("Test: get all Creators (2 results")
    public void testGetAllCreators() {

        Mockito.when(this.repository.findAll()).thenReturn(this.testCreatorList);

        List<Creator> creators = this.service.getAll();

        Assertions.assertThat(creators).isNotNull();
        Assertions.assertThat(creators.size()).isEqualTo(2);
        Assertions.assertThat(creators.get(0).getId().toString()).isEqualTo("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1");
        Assertions.assertThat(creators.get(0).getName()).isEqualTo("Test Creator 01");
        Assertions.assertThat(creators.get(1).getId().toString()).isEqualTo("1eeaabc2-1093-4692-858b-e21cdee7ead6");
        Assertions.assertThat(creators.get(1).getName()).isEqualTo("Test Creator 02");

        Mockito.verify(this.repository, Mockito.times(1)).findAll();
    }

    /**
     * Test the Service Layer of the Creator to get all creators
     * This case will assume that the returned list is empty
     */
    @Test
    @DisplayName("Test: get all Creators (0 results)")
    public void testGetAllCreatorsEmpty() {

        Mockito.when(this.repository.findAll()).thenReturn(Collections.emptyList());

        List<Creator> creators = this.service.getAll();

        Assertions.assertThat(creators).isNotNull();
        Assertions.assertThat(creators).isEmpty();

        Mockito.verify(this.repository, Mockito.times(1)).findAll();
    }

    /**
     * Test the Service Layer of the Creator to get one specific Creator by its ID
     */
    @Test
    @DisplayName("Test: get one Creator")
    public void testGetOneCreator() {

        Mockito.when(this.repository.findById(this.testCreator.getId())).thenReturn(Optional.of(this.testCreator));

        Creator creator = this.service.getOne(this.testCreator.getId());

        Assertions.assertThat(creator).isNotNull();
        Assertions.assertThat(creator.getId()).isEqualTo(UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1"));
        Assertions.assertThat(creator.getName()).isEqualTo("Test Creator 01");

        Mockito.verify(this.repository, Mockito.times(1)).findById(this.testCreator.getId());
    }

    /**
     * Test the Service Layer of the Creator to get one non-existing Creator
     */
    @Test
    @DisplayName("Test: get one Creator that does not exist")
    public void testGetOneMissingCreator() {

        UUID id = UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1");

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> this.service.getOne(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource with the id 'eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1' could not be found");

        Mockito.verify(this.repository, Mockito.times(1)).findById(id);
    }

    /**
     * Test the Service Layer of the Creator to add a new Creator
     */
    @Test
    @DisplayName("Test: add a new Creator")
    public void testAddNewCreator() {

        Creator creator = new Creator(UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1"), "Test Creator Name 01");

        Mockito.when(this.repository.save(creator)).thenReturn(creator);

        Creator savedCreator = this.service.addNewCreator(creator);

        Assertions.assertThat(savedCreator).isNotNull();
        Assertions.assertThat(savedCreator.getId()).isEqualTo(UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1"));
        Assertions.assertThat(creator.getName()).isEqualTo("Test Creator Name 01");

        Mockito.verify(this.repository, Mockito.times(1)).save(Mockito.any(Creator.class));
    }

    /**
     * Test to update an existing Creator
     */
    @Test
    @DisplayName("Test: update an existing Creator")
    public void testUpdateCreator() {
        UUID id = UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1");

        Creator creator = new Creator(id, "Test Creator Name 01");

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.of(creator));
        Mockito.when(this.repository.save(creator)).thenReturn(creator);

        Creator updatedCreator = this.service.updateCreator(id, creator);

        Assertions.assertThat(updatedCreator).isNotNull();
        Assertions.assertThat(updatedCreator.getId()).isEqualTo(id);
        Assertions.assertThat(updatedCreator.getName()).isEqualTo("Test Creator Name 01");
    }

    /**
     * Test to update a missing Creator
     */
    @Test
    @DisplayName("Test: update a missing Creator")
    public void testUpdateMissingCreator() {
        UUID id = UUID.fromString("eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1");

        Creator creator = new Creator(id, "Test Creator Name 01");

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> this.service.updateCreator(id, creator)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource with the id 'eeb41c5f-9026-4cf1-9da1-23a2ef0cd9c1' could not be found");
    }
}