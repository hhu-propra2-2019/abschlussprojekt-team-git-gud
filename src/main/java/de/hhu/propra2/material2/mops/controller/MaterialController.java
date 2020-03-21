package de.hhu.propra2.material2.mops.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hhu.propra2.material2.mops.Exceptions.DownloadException;
import de.hhu.propra2.material2.mops.Exceptions.NoUploadPermissionException;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.domain.models.UploadForm;
import de.hhu.propra2.material2.mops.domain.services.MinioDownloadService;
import de.hhu.propra2.material2.mops.domain.services.ModelService;
import de.hhu.propra2.material2.mops.security.Account;
import de.hhu.propra2.material2.mops.web.dto.GroupWebDTO;
import de.hhu.propra2.material2.mops.web.dto.UpdatedGroupRequestMapper;
import de.hhu.propra2.material2.mops.web.dto.UserWebDTO;
import de.hhu.propra2.material2.mops.domain.services.UploadService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
    private MinioDownloadService minioDownloadService;

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
        model.addAttribute("tagText", modelService.getAlleTagsByUser(token));
        model.addAttribute("error", errorMessage);
        model.addAttribute("success", successMessage);
        try {
            uploadService.startUpload(upForm, uploader.getName());
            setMessages(null, "Upload war erfolgreich!");
        } catch (FileUploadException e) {
            setMessages("Beim Upload gab es ein Problem", null);
        } catch (SQLException e) {
            setMessages("Beim Upload gab es ein Problem", null);
        } catch (NoUploadPermissionException e) {
            setMessages("Sie sind nicht berechtig in dieser Gruppe hochzuladen!", null);
        }
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

    /**
     *
     */
    @GetMapping("/files")
    @RolesAllowed( {"ROLE_orga", "ROLE_studentin", "ROLE_actuator"})
    public void getFile(
            final Long fileId,
            final HttpServletResponse response, final KeycloakAuthenticationToken token) {
        try {
            // get your file as InputStream
            InputStream input = minioDownloadService.getObject(fileId);
            // copy it to response's OutputStream
            FileCopyUtils.copy(input, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException | DownloadException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    /**
     * @param 
     * @param
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/yolo")
    @ResponseBody
    public String ismiregal() throws JsonProcessingException {

        UserWebDTO donald = new UserWebDTO("789@yahoo.de", "Trump", "Donald", "0");
        UserWebDTO matvey = new UserWebDTO("123@yahoo.de", "Lorkish", "Matvey", "1");
        UserWebDTO torben = new UserWebDTO("456@yahoo.de", "Schmitz", "Torben", "2");
        UserWebDTO[] user1 = {matvey, torben};
        UserWebDTO[] user2 = {donald};

        Map<String, String> belegungenUndRechte1 = new HashMap<>();
        belegungenUndRechte1.put(matvey.getUserId(), "MEMBER");
        belegungenUndRechte1.put(torben.getUserId(), "MEMBER");
        Map<String, String> belegungenUndRechte2 = new HashMap<>();
        belegungenUndRechte2.put(donald.getUserId(), "ADMIN");

        GroupWebDTO gruppe1 = new GroupWebDTO("Toll",
                1,
                Arrays.asList(user1),
                1,
                belegungenUndRechte1,
                "TolleGruppe",
                "SIMPLE",
                "PUBLIC");
        GroupWebDTO gruppe2 = new GroupWebDTO("Nice",
                2,
                Arrays.asList(user2),
                2,
                belegungenUndRechte2,
                "DoofeGruppe",
                "LECTURE",
                "PRIVATE");
        GroupWebDTO[] gruppenArray = {gruppe1, gruppe2};

        UpdatedGroupRequestMapper sample = new UpdatedGroupRequestMapper(Arrays.asList(gruppenArray), 1);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(sample));
        return mapper.writeValueAsString(sample);
    }

    private void setMessages(final String pErrorMessage, final String pSuccessMessage) {
        this.errorMessage = pErrorMessage;
        this.successMessage = pSuccessMessage;
    }

    private void resetMessages() {
        this.errorMessage = null;
        this.successMessage = null;
    }
}
