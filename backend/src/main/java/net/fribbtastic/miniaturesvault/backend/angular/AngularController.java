package net.fribbtastic.miniaturesvault.backend.angular;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Frederic Eßer
 */
@Controller
public class AngularController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }
}
