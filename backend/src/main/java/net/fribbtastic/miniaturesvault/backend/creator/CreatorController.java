package net.fribbtastic.miniaturesvault.backend.creator;

import com.github.lkqm.spring.api.version.ApiVersion;
import net.fribbtastic.miniaturesvault.backend.response.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@ApiVersion("1")
public class CreatorController {

    private static final Logger LOGGER = LogManager.getLogger(CreatorController.class);

    @Autowired
    private CreatorServiceImpl service;

    /**
     * Get all currently available Creators from the Service
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
}
