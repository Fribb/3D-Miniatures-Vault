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

    @Override
    public Creator getOne(UUID id) {
        LOGGER.debug("calling service layer [getOne]");

        return this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
