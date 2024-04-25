package net.fribbtastic.miniaturesvault.backend.creator;

import com.github.lkqm.spring.api.version.ApiVersion;
import net.fribbtastic.miniaturesvault.backend.response.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Frederic Eßer
 */
@RestController
@RequestMapping(path = "/creator", produces = "application/json")
@ApiVersion("1")
public class CreatorController {

    private static final Logger LOGGER = LogManager.getLogger(CreatorController.class);

    @Autowired
    private CreatorServiceImpl service;

    /**
     * Get all currently available Creators from the Service layer
     *
     * @return a ResponseEntity with the list of Creators
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Creator>>> getALlCreators() {
        LOGGER.debug("new request [getAllCreators]");

        List<Creator> creatorList = this.service.getAll();

        ApiResponse<List<Creator>> response = ApiResponse.createSuccessResponse(HttpStatus.OK, creatorList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Get a Creator by its Unique ID from the Service layer
     *
     * @param id the {@link UUID} of the Creator
     * @return the {@link ApiResponse} with the Creator wrapped in a {@link ResponseEntity}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Creator>> getOneCreator(@PathVariable UUID id) {
        LOGGER.debug("new request [getOneCreator]");

        Creator creator = this.service.getOne(id);

        ApiResponse<Creator> response = ApiResponse.createSuccessResponse(HttpStatus.OK, creator);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Add the POSTed Creator and return it in the response
     *
     * @param creator the Creator to be added in the Request Body
     * @return the {@link ApiResponse} with the added Creator wrapped in a {@link ResponseEntity}
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Creator>> addNewCreator(@RequestBody Creator creator) {
        Creator newCreator = this.service.addNewCreator(creator);

        ApiResponse<Creator> response = ApiResponse.createSuccessResponse(HttpStatus.CREATED, newCreator);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Update the Creator with the specific ID with the information passed with the RequestBody
     *
     * @param id the ID of the Creator that should be updated
     * @param creator the Creator Object with the new information
     * @return the {@link ApiResponse} with the updated Creator wrapped in a {@link ResponseEntity}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Creator>> updateCreator(@PathVariable UUID id, @RequestBody Creator creator) {
        Creator updatedCreator = this.service.updateCreator(id, creator);

        ApiResponse<Creator> response = ApiResponse.createSuccessResponse(HttpStatus.OK, updatedCreator);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Delete the Creator with the specific ID passed with the {@link RequestBody}
     *
     * @param id the ID of the Creator that should be deleted
     * @return the {@link ApiResponse} (will 'null' data) with the updated Creator wrapped in a {@link ResponseEntity}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCreator(@PathVariable UUID id) {
        this.service.deleteCreator(id);

        ApiResponse<?> response = ApiResponse.createSuccessResponse(HttpStatus.OK, null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
