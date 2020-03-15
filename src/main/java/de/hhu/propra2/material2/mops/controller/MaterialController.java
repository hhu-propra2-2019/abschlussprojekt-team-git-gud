package de.hhu.propra2.material2.mops.controller;

import de.hhu.propra2.material2.mops.database.DateiRepository;
import de.hhu.propra2.material2.mops.database.GruppeRepository;
import de.hhu.propra2.material2.mops.database.TagRepository;
import de.hhu.propra2.material2.mops.database.UserRepository;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.domain.models.UploadForm;
import de.hhu.propra2.material2.mops.security.Account;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;


@Controller
public class MaterialController {

    private final Counter authenticatedAccess;
    private final Counter publicAccess;
    private final GruppeRepository gruppeRepository;
    private final DateiRepository dateiRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public MaterialController(final MeterRegistry registry, final GruppeRepository gruppeRepo,
                              final DateiRepository dateiRepo, final TagRepository tagRepo,
                              final UserRepository userRepo) {
        authenticatedAccess = registry.counter("access.authenticated");
        publicAccess = registry.counter("access.public");
        this.gruppeRepository = gruppeRepo;
        this.dateiRepository = dateiRepo;
        this.tagRepository = tagRepo;
        this.userRepository = userRepo;
    }

    /**
     * Nimmt das Authentifizierungstoken von Keycloak und erzeugt ein AccountDTO
     * für die Views.
     *
     * @param token keycloak token injection
     * @return neuen Account der im Template verwendet wird
     */
    private Account createAccountFromPrincipal(final KeycloakAuthenticationToken token) {
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        return new Account(
                principal.getName(),
                principal.getKeycloakSecurityContext().getIdToken().getEmail(),
                null,
                token.getAccount().getRoles());
    }

    /**start routing.
     * @return String
     */
    @GetMapping("/")
    public String startseite(final KeycloakAuthenticationToken token, final Model model) {
        if (token != null) {
            model.addAttribute("account", createAccountFromPrincipal(token));
        }
        publicAccess.increment();
        //model.addAttribute("gruppen", gruppeRepository.findById(804187799L));
        return "start";
    }

    /**Shows the documents of a Group.
     * @return String
     */
    @GetMapping("/dateiSicht")
    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    public String sicht(final KeycloakAuthenticationToken token, final Model model) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();
        model.addAttribute("gruppen", gruppeRepository.findAll());
        return "dateiSicht";
    }

    /**search page.
     * @return String
     */
    @GetMapping("/suche")
    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    public String vorSuche(final KeycloakAuthenticationToken token, final Model model) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();
        model.addAttribute("gruppen", gruppeRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("dateiTypen", dateiRepository.findAll());
        model.addAttribute("uploader", userRepository.findAll());
        return "suche";
    }

    /**page for search results.
     * @return String
    */
    @PostMapping("/suche")
    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    public String suchen(
            final KeycloakAuthenticationToken token, final Model model, final @ModelAttribute Suche suchen) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();
        model.addAttribute("gruppen", gruppeRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("dateiTypen", dateiRepository.findAll());
        model.addAttribute("uploader", userRepository.findAll());
        return "redirect:/suche";
    }

    /**updload page.
     * @return String
     */
    @GetMapping("/upload")
    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    public String upload(final KeycloakAuthenticationToken token, final Model model) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();
        model.addAttribute("gruppen", gruppeRepository.findAll());
        model.addAttribute("uploader", userRepository.findAll());
        model.addAttribute("dateitypen", dateiRepository.findAll());
        return "upload";
    }

    /** upload routing
     * @param token injected keycloak token
     * @param model injected thymeleaf model
     * @return upload routing
     */

    @PostMapping("/upload")
    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    public String upload(final KeycloakAuthenticationToken token, final Model model, final UploadForm upForm) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();
        System.out.println(upForm);
        return "redirect:/upload";
    }

    /**route to logout.
     * @param request logout request
     * @return  homepage routing
     * @throws Exception no handling
     */
    @GetMapping("/logout")
    public String logout(final HttpServletRequest request) throws Exception {
        request.logout();
        return "redirect:/";
    }
}
