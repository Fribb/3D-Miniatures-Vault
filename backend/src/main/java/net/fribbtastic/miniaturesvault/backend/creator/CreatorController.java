package net.fribbtastic.miniaturesvault.backend.creator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Frederic Eßer
 */
@RestController
@RequestMapping(path = "/creator", produces = "application/json")
public class CreatorController {

    @Autowired
    private CreatorServiceImpl service;

    /**
     * Get all currently available Creators from the Service
     *
     * @return a ResponseEntity with the list of Creators
     */
    @GetMapping
    public ResponseEntity<List<Creator>> getALlCreators() {
        List<Creator> creatorList = this.service.getAll();

        return new ResponseEntity<>(creatorList, HttpStatus.OK);
    }
}
