package de.hhu.propra2.material2.mops.controller;

import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MaterialController {

    /**start routing.
     * @return String
     */
    @GetMapping("/")
    public String startseite(final Model model) {
        List<Gruppe> gruppen =  new ArrayList<>();
        gruppen.add(new Gruppe(1L, "ProPra", null));
        gruppen.add(new Gruppe(2L, "Hard Prog", null));
        model.addAttribute("gruppen", gruppen);
        return "start";
    }

    /**Shows the documents of a Group.
     * @return String
     */
    @GetMapping("/dateiSicht")
    public String sicht(final Model model) {
        List<Gruppe> gruppen =  new ArrayList<>();
        gruppen.add(new Gruppe(1L, "ProPra", null));
        gruppen.add(new Gruppe(2L, "Hard Prog", null));
        model.addAttribute("gruppen", gruppen);
        return "dateiSicht";
    }

    /**starting page.
     * @return String
     */
    @GetMapping("/suche")
    public String vorSuche(final Model model) {
        List<Gruppe> gruppen =  new ArrayList<>();
        gruppen.add(new Gruppe(1L, "ProPra", null));
        gruppen.add(new Gruppe(2L, "Hard Prog", null));
        model.addAttribute("gruppen", gruppen);
        return "suche";
    }

    /**rout to base.
     * @return String
     */
    @PostMapping("/suche")
    public String vorSuchePost(final Model model) {
        List<Gruppe> gruppen =  new ArrayList<>();
        gruppen.add(new Gruppe(1L, "ProPra", null));
        gruppen.add(new Gruppe(2L, "Hard Prog", null));
        model.addAttribute("gruppen", gruppen);
        return "base";
    }
}
