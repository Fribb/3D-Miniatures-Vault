package net.fribbtastic.miniaturesvault.backend.creator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Frederic Eßer
 */
public class CreatorTest {

    @Test
    @DisplayName("Test: new Creator with name")
    public void testNewCreator() {

        Creator creator = new Creator("Test Creator 01");

        Assertions.assertThat(creator).isNotNull();
        Assertions.assertThat(creator.getName()).isEqualTo("Test Creator 01");
    }
}
