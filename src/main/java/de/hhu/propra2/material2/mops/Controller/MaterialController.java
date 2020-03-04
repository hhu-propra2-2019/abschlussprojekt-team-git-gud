package de.hhu.propra2.material2.mops.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MaterialController {

    /**base routing.
     * @return String
     */
    @GetMapping("/")
    public String index() {
        return "base";
    }
}
