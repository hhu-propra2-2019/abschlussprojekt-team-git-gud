package de.hhu.propra2.material2.mops.controller;

import de.hhu.propra2.material2.mops.Exceptions.DownloadException;
import de.hhu.propra2.material2.mops.Exceptions.HasNoGroupToUploadException;
import de.hhu.propra2.material2.mops.Exceptions.NoUploadPermissionException;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.domain.models.UpdateForm;
import de.hhu.propra2.material2.mops.domain.models.UploadForm;
import de.hhu.propra2.material2.mops.domain.services.MinIOService;
import de.hhu.propra2.material2.mops.domain.services.ModelService;
import de.hhu.propra2.material2.mops.domain.services.UpdateService;
import de.hhu.propra2.material2.mops.domain.services.UploadService;
import de.hhu.propra2.material2.mops.security.Account;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * After the @RolesAllowed Annotation the Syle Code wants a space after
 * the opening bracket, but that throwas the ParenPad Warning, so we cannot
 * satisfy both conditions and have to disable one
 */
@Controller
@SuppressWarnings("checkstyle:ParenPad")
public class MaterialController {

    @Autowired
    private RestTemplate serviceAccountRestTemplate;

    @Autowired
    private ModelService modelService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UpdateService updateService;
    @Autowired
    private MinIOService minIOService;

    private String errorMessage;
    private String successMessage;

    /**
     * start routing.
     *
     * @return String
     */
    @GetMapping("/")
    public String startseite(final KeycloakAuthenticationToken token, final Model model) {
        if (token != null) {
            model.addAttribute("account", modelService.getAccountFromKeycloak(token));
            model.addAttribute("gruppen", modelService.getAlleGruppenByUser(token));
        }
        model.addAttribute("error", errorMessage);
        model.addAttribute("success", successMessage);
        resetMessages();
        return "start";
    }

    /**
     * Shows the documents of a Group.
     *
     * @return String
     */
    @GetMapping("/dateiSicht")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin", "ROLE_actuator"})
    public String sicht(final KeycloakAuthenticationToken token, final Model model, final Long gruppenId) {
        model.addAttribute("account", modelService.getAccountFromKeycloak(token));
        model.addAttribute("gruppen", modelService.getAlleGruppenByUser(token));
        model.addAttribute("kategorien", modelService.getKategorienByGruppe(gruppenId, token));
        model.addAttribute("dateien", modelService.getAlleDateienByGruppe(gruppenId, token));
        Gruppe gruppenAuswahl = modelService.getGruppeByUserAndGroupID(gruppenId, token);
        model.addAttribute("gruppenAuswahl", gruppenAuswahl);
        model.addAttribute("error", errorMessage);
        model.addAttribute("success", successMessage);
        resetMessages();
        return "dateiSicht";
    }

    /**
     * search page.
     *
     * @return String
     */
    @GetMapping("/suche")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin", "ROLE_actuator"})
    public String vorSuche(final KeycloakAuthenticationToken token, final Model model) {
        model.addAttribute("account", modelService.getAccountFromKeycloak(token));
        model.addAttribute("gruppen", modelService.getAlleGruppenByUser(token));
        model.addAttribute("tags", modelService.getAlleTagsByUser(token));
        model.addAttribute("dateiTypen", modelService.getAlleDateiTypenByUser(token));
        model.addAttribute("uploader", modelService.getAlleUploaderByUser(token));
        return "suche";
    }

    /**
     * page for search results.
     *
     * @return String
     */
    @PostMapping("/suche")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin", "ROLE_actuator"})
    public String suchen(
            final KeycloakAuthenticationToken token, final Model model, final @ModelAttribute Suche suchen,
            final String search) {
        model.addAttribute("account", modelService.getAccountFromKeycloak(token));
        model.addAttribute("gruppen", modelService.getAlleGruppenByUser(token));
        model.addAttribute("tags", modelService.getAlleTagsByUser(token));
        model.addAttribute("dateiTypen", modelService.getAlleDateiTypenByUser(token));
        model.addAttribute("uploader", modelService.getAlleUploaderByUser(token));
        if (search == null) {
            model.addAttribute("suche", suchen);
            return "redirect:/suche";
        }
        return "redirect:/suche";
    }

    /**
     * updload page.
     *
     * @return String
     */
    @GetMapping("/upload")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin", "ROLE_actuator"})
    public String upload(final KeycloakAuthenticationToken token, final Model model) {
        model.addAttribute("account", modelService.getAccountFromKeycloak(token));
        model.addAttribute("gruppen", modelService.getAlleGruppenByUser(token));
        model.addAttribute("gruppenUpload", modelService.getAlleUploadGruppenByUser(token));
        model.addAttribute("tagText", modelService.getAlleTagsByUser(token));
        model.addAttribute("error", errorMessage);
        model.addAttribute("success", successMessage);
        resetMessages();
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
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin", "ROLE_actuator"})
    public String upload(final KeycloakAuthenticationToken token, final Model model, final UploadForm upForm) {
        Account uploader = modelService.getAccountFromKeycloak(token);
        model.addAttribute("account", modelService.getAccountFromKeycloak(token));
        model.addAttribute("gruppen", modelService.getAlleGruppenByUser(token));
        model.addAttribute("gruppenUpload", modelService.getAlleUploadGruppenByUser(token));
        model.addAttribute("tagText", modelService.getAlleTagsByUser(token));
        try {
            uploadService.startUpload(upForm, uploader.getName());
            setMessages(null, "Upload war erfolgreich!");
        } catch (FileUploadException e) {
            setMessages("Beim Upload gab es ein Problem.", null);
        } catch (SQLException e) {
            setMessages("Beim speichern in der Datenbank gab es einen Fehler.", null);
        } catch (NoUploadPermissionException e) {
            setMessages("Sie sind nicht berechtigt in dieser Gruppe hochzuladen!", null);
        } catch (HasNoGroupToUploadException e) {
            setMessages("Sie haben keine Gruppe, auf die sie hochladen können!", null);
        }

        model.addAttribute("error", errorMessage);
        model.addAttribute("success", successMessage);
        resetMessages();
        return "upload";
    }

    /**
     * update page.
     *
     * @param token     injected keycloak token
     * @param model     injected thymeleaf model
     * @param gruppenId id of the group where the file is saved
     * @param dateiId   id of the file
     * @return update page
     */
    @GetMapping("/update")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin"})
    public String update(final KeycloakAuthenticationToken token,
                         final Model model,
                         final Long gruppenId,
                         final Long dateiId) {
        model.addAttribute("account", modelService.getAccountFromKeycloak(token));
        model.addAttribute("tagText", modelService.getAlleTagsByUser(token));
        Datei datei = modelService.getDateiById(dateiId, token);
        if (datei == null) {
            setMessages("Sie haben keine Zugriffsberechtigung.", null);
            model.addAttribute("error", errorMessage);
            model.addAttribute("success", successMessage);
            String url = "redirect:/dateiSicht?gruppenId=%d";
            return String.format(url, gruppenId);
        }
        model.addAttribute("datei", datei);
        return "update";
    }

    /**
     * update routing.
     *
     * @param token      injected keycloak token
     * @param model      injected thymeleaf model
     * @param updateForm form for update
     * @param gruppenId  id of the group where the file is saved
     * @param dateiId    id of the file
     * @return update page
     */
    @PostMapping("/update")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin"})
    public String update(final KeycloakAuthenticationToken token,
                         final Model model,
                         final UpdateForm updateForm,
                         final Long gruppenId,
                         final Long dateiId) {
        Account userAccount = modelService.getAccountFromKeycloak(token);
        model.addAttribute("account", userAccount);
        model.addAttribute("tagText", modelService.getAlleTagsByUser(token));
        try {
            updateService.startUpdate(updateForm, userAccount.getName(), gruppenId, dateiId);
            setMessages(null, "Edit erfolgreich.");
        } catch (SQLException e) {
            setMessages("Es gab ein Problem beim Update.", null);
        } catch (NoUploadPermissionException e) {
            setMessages("Sie sind nicht berechtigt diese Datei zu verändern.", null);
        }
        model.addAttribute("error", errorMessage);
        model.addAttribute("success", successMessage);
        String url = "redirect:/dateiSicht?gruppenId=%d";
        return String.format(url, gruppenId);
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

    /**
     *
     */
    @GetMapping(value = "/files")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin", "ROLE_actuator"})
    public ResponseEntity<InputStreamResource> getFile(final Long fileId,
                                                       final KeycloakAuthenticationToken token)
            throws DownloadException, SQLException {
        InputStream input = new BufferedInputStream(minIOService.getObject(fileId));
        Datei file = modelService.getDateiById(fileId, token);
        return ResponseEntity.ok()
                .header("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""))
                .contentLength(file.getDateigroesse())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(input));
    }

    private void setMessages(final String pErrorMessage, final String pSuccessMessage) {
        this.errorMessage = pErrorMessage;
        this.successMessage = pSuccessMessage;
    }

    private void resetMessages() {
        this.errorMessage = null;
        this.successMessage = null;
    }

    /**
     * exception handler for a download error.
     *
     * @param e exception
     * @return redirect to home page with a error message
     */
    @ExceptionHandler(DownloadException.class)
    String handleDonwloadException(final DownloadException e) {
        setMessages("Beim Download gab es ein Problem.", null);
        return "redirect:/";
    }

    /**
     * exception handler for a sql error.
     *
     * @param e exception
     * @return redirect to home page with a error message
     */
    @ExceptionHandler(SQLException.class)
    String handleSQLException(final SQLException e) {
        setMessages("Beim zugriff auf die Datenbank gab es ein Problem.", null);
        return "redirect:/";
    }
}
