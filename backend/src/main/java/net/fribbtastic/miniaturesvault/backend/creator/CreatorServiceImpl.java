package net.fribbtastic.miniaturesvault.backend.creator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frederic Eßer
 */
@Service
public class CreatorServiceImpl implements CreatorService {

    @Autowired
    private CreatorRepository repository;

    /**
     * get all available Creators from the Database
     *
     * @return a list of Creator Objects
     */
    @Override
    public List<Creator> getAll() {

        return new ArrayList<>(this.repository.findAll());
    }
}
