package net.fribbtastic.miniaturesvault.backend.creator;

import java.util.List;
import java.util.UUID;

/**
 * @author Frederic Eßer
 */

public interface CreatorService {

    List<Creator> getAll();
    Creator getOne(UUID id);
    Creator addNewCreator(Creator creator);
    Creator updateCreator(UUID id, Creator creator);
    void deleteCreator(UUID id);
}
