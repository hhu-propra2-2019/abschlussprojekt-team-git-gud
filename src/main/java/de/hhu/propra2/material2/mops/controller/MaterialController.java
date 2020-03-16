package de.hhu.propra2.material2.mops.controller;

import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.domain.models.UploadForm;
import de.hhu.propra2.material2.mops.domain.models.User;
import de.hhu.propra2.material2.mops.domain.services.IModelService;
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
import java.util.List;
import java.util.Set;

@Controller
@SuppressWarnings("checkstyle:ParenPad")
/**
 * After the @RolesAllowed Annotation the Syle Code wants a space after
 * the opening bracket, but that throwas the ParenPad Warning, so we cannot
 * satisfy both conditions and have to disable one
 */
public class MaterialController {

    private final Counter authenticatedAccess;
    private final Counter publicAccess;

    private final List<Gruppe> gruppen;
    private final Set<String> tags;
    private final Set<String> dateiTypen;
    private final Set<String> uploader;

    public MaterialController(final MeterRegistry registry, final IModelService ms) {
        authenticatedAccess = registry.counter("access.authenticated");
        publicAccess = registry.counter("access.public");

        User user = ms.createDummyUser();
        gruppen = user.getAllGruppen();
        tags = ms.getAlleTagsByUser(user);
        dateiTypen = ms.getAlleDateiTypenByUser(user);
        uploader = ms.getAlleUploaderByUser(user);
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

    /**
     * start routing.
     *
     * @return String
     */
    @GetMapping("/")
    public String startseite(final KeycloakAuthenticationToken token, final Model model) {
        if (token != null) {
            model.addAttribute("account", createAccountFromPrincipal(token));
        }
        publicAccess.increment();
        model.addAttribute("gruppen", gruppen);
        return "start";
    }

    /**
     * Shows the documents of a Group.
     *
     * @return String
     */
    @GetMapping("/dateiSicht")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin"})
    public String sicht(final KeycloakAuthenticationToken token, final Model model) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();
        model.addAttribute("gruppen", gruppen);
        return "dateiSicht";
    }

    /**
     * search page.
     *
     * @return String
     */

    @GetMapping("/suche")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin"})
    public String vorSuche(final KeycloakAuthenticationToken token, final Model model) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();

        model.addAttribute("gruppen", gruppen);
        model.addAttribute("tags", tags);
        model.addAttribute("dateiTypen", dateiTypen);
        model.addAttribute("uploader", uploader);
        return "suche";
    }

    /**
     * page for search results.
     *
     * @return String
     */
    @PostMapping("/suche")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin"})
    public String suchen(
            final KeycloakAuthenticationToken token, final Model model, final @ModelAttribute Suche suchen) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();
        model.addAttribute("gruppen", gruppen);
        model.addAttribute("tags", tags);
        model.addAttribute("dateiTypen", dateiTypen);
        model.addAttribute("uploader", uploader);
        return "redirect:/suche";
    }

    /**
     * updload page.
     *
     * @return String
     */
    @GetMapping("/upload")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin"})
    public String upload(final KeycloakAuthenticationToken token, final Model model) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();
        model.addAttribute("gruppen", gruppen);

        model.addAttribute("tagText", tags);

        model.addAttribute("uploader", uploader);
        model.addAttribute("dateitypen", dateiTypen);
        return "upload";
    }

    /**
     * upload routing
     *
     * @param token injected keycloak token
     * @param model injected thymeleaf model
     * @return upload routing
     */
    @PostMapping("/upload")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin"})
    public String upload(final KeycloakAuthenticationToken token, final Model model, final UploadForm upForm) {
        model.addAttribute("account", createAccountFromPrincipal(token));
        authenticatedAccess.increment();
        System.out.println(upForm);
        return "redirect:/upload";
    }

    /**
     * route to logout.
     *
     * @param request logout request
     * @return homepage routing
     * @throws Exception no handling
     */
    @GetMapping("/logout")
    public String logout(final HttpServletRequest request) throws Exception {
        request.logout();
        return "redirect:/";
    }
}
