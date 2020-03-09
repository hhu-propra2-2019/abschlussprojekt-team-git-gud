package de.hhu.propra2.material2.mops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MaterialController {

    /**start routing.
     * @return String
     */
    @GetMapping("/")
    public String startseite() {
        return "start";
    }

    /**Shows the documents of a Group.
     * @return String
     */
    @GetMapping("/dateiSicht")
    public String sicht() {
        return "dateiSicht";
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
