package de.hhu.propra2.material2.mops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MaterialController {

    /**base routing.
     * @return String
     */
    @GetMapping("/")
    public String index() {
        return "base";
    }

    /**starting page.
     * @return String
     */
    @GetMapping("/suche")
    public String vorSuche() {
        return "suche";
    }

    /**rout to base.
     * @return String
     */
    @PostMapping("/suche")
    public String vorSuchePost() {
        return "base";
    }
}
