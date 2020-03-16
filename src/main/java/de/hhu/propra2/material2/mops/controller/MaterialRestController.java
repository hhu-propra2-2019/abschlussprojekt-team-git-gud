package de.hhu.propra2.material2.mops.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MaterialRestController {

    /*
     * beispiel für einen Rest-Controller aus der Keycloak-Demo
     * Gegebenenfalls für Portfolio einrichten!
     *
     @GetMapping("specific-text/{some_id}")
     @Secured("ROLE_keycloak_demo_api_user")
     public List<Entry> generateTextWithId(KeycloakAuthenticationToken token, @PathVariable("some_id") Long id) {
     Entry userEntry = new Entry("request was send by user:",token.getName(),"");
     var generatedEntries = Entry.generate(10).stream()
     .map(e -> new Entry("From Keycloak-Demo : " + id + " " + e.getAttribute1(),
     "From Keycloak-Demo : " + id + " " + e.getAttribute2(),
     "From Keycloak-Demo : " + id + " " + e.getAttribute3()))
     .collect(Collectors.toList());
     generatedEntries.add(0,userEntry);
     return generatedEntries;
     }
     */
}
