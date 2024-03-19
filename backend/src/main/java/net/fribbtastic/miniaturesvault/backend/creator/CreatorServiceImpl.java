package net.fribbtastic.miniaturesvault.backend.creator;

import net.fribbtastic.miniaturesvault.backend.exceptions.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Frederic Eßer
 */
@Service
public class CreatorServiceImpl implements CreatorService {

    private static final Logger LOGGER = LogManager.getLogger(CreatorServiceImpl.class);

    @Autowired
    private CreatorRepository repository;

    /**
     * get all available Creators from the Database
     *
     * @return a list of Creator Objects
     */
    @Override
    public List<Creator> getAll() {
        LOGGER.debug("calling service layer [getAll]");

        return new ArrayList<>(this.repository.findAll());
    }

    /**
     * get a Creator by its ID
     *
     * @param id the ID of the Creator
     * @return the Creator Object
     */
    @Override
    public Creator getOne(UUID id) {
        LOGGER.debug("calling service layer [getOne]");

        return this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * Add a new Creator
     *
     * @param creator the Creator that should be added
     * @return the added Creator Objet
     */
    @Override
    public Creator addNewCreator(Creator creator) {
        return this.repository.save(creator);
    }

    /**
     * Update an existing Creator
     *
     * @param id the ID of the creator that should be updated
     * @param creator the creator Object that should be used to update the existing information
     * @return the updated Creator Object
     */
    @Override
    public Creator updateCreator(UUID id, Creator creator) {
        return this.repository.findById(id)
                .map(c -> {
                    creator.setId(id);
                    c = creator;
                    this.repository.save(c);
                    return c;
                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
