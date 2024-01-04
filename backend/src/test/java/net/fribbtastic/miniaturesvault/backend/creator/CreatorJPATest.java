package net.fribbtastic.miniaturesvault.backend.creator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

/**
 * @author Frederic Eßer
 */
@DataJpaTest
public class CreatorJPATest {

    @Autowired
    private CreatorRepository repository;

    private Creator creator;

    @BeforeEach
    public void setUpEach() {
        this.creator = Creator.builder()
                .name("Test Creator Name 01")
                .build();
    }

    @Test
    @DisplayName("Test: Inject Components are not Null")
    public void testInjectComponentsNotNull() {

        Assertions.assertThat(this.repository).isNotNull();
    }

    @Test
    @DisplayName("Test: Save a new Creator in DB")
    public void testSaveNewCreator() {

        // save the creator
        Creator savedCreator = this.repository.save(this.creator);

        Assertions.assertThat(savedCreator).isNotNull();
        Assertions.assertThat(savedCreator.getId()).isGreaterThan(0);
        Assertions.assertThat(savedCreator.getName()).isEqualTo("Test Creator Name 01");
    }

    @Test
    @DisplayName("Test: get all Creators")
    public void testGetAllCreators() {

        // create a new Creator
        Creator c2 = Creator.builder()
                .name("Test Creator Name 02")
                .build();

        // save creators
        this.repository.save(this.creator);
        this.repository.save(c2);

        // get all creators
        List<Creator> creatorList = this.repository.findAll();

        Assertions.assertThat(creatorList).isNotNull();
        Assertions.assertThat(creatorList.size()).isEqualTo(2);
        Assertions.assertThat(creatorList.get(0).getName()).isEqualTo("Test Creator Name 01");
        Assertions.assertThat(creatorList.get(1).getName()).isEqualTo("Test Creator Name 02");
    }

    @Test
    @DisplayName("Test: get Creator by ID")
    public void testGetCreatorById() {

        // save the creator
        this.repository.save(this.creator);

        // get the creator with the ID from DB
        Creator creatorFromDb = this.repository.findById(this.creator.getId()).orElse(null);

        Assertions.assertThat(creatorFromDb).isNotNull();
        Assertions.assertThat(creatorFromDb.getName()).isEqualTo("Test Creator Name 01");
    }

    @Test
    @DisplayName("Test: update Creator information")
    public void testUpdateCreator() {

        // save the creator
        this.repository.save(this.creator);

        // get the creator with the ID from DB
        Creator creatorFromDb = this.repository.findById(this.creator.getId()).orElse(null);

        Assertions.assertThat(creatorFromDb).isNotNull();
        Assertions.assertThat(creatorFromDb.getName()).isEqualTo("Test Creator Name 01");

        // set a different name for the Creator
        creatorFromDb.setName("Some New Name");

        // save the updated Creator
        Creator updatedCreator = this.repository.save(creatorFromDb);

        Assertions.assertThat(updatedCreator).isNotNull();
        Assertions.assertThat(updatedCreator.getName()).isEqualTo("Some New Name");
    }

    @Test
    @DisplayName("Test: delete Creator")
    public void testDeleteCreator() {

        // save the creator
        this.repository.save(this.creator);

        Assertions.assertThat(this.creator).isNotNull();
        Assertions.assertThat(this.creator.getId()).isGreaterThan(0);

        // delete the creator
        this.repository.deleteById(this.creator.getId());

        // try to find the creator
        Optional<Creator> creatorOptional = this.repository.findById(this.creator.getId());

        Assertions.assertThat(creatorOptional).isEmpty();
    }
}
