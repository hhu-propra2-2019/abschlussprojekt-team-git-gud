package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.Repository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import de.hhu.propra2.material2.mops.security.Account;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public final class ModelService implements IModelService {


    private final Repository repository;
    private final SuchService suchService;
    private Suche suche;

    /**
     * Constructor of ModelService.
     */
    public ModelService(final Repository repositoryArg, final SuchService suchServiceArg) {
        repository = repositoryArg;
        suchService = suchServiceArg;
    }

    private Datei loadDatei(final DateiDTO dateiDTO) {
        List<Tag> tags = dateiDTO.getTagDTOs().stream()
                .map(this::loadTag)
                .collect(Collectors.toList());
        return new Datei(
                dateiDTO.getId(),
                dateiDTO.getName(),
                loadUploader(dateiDTO.getUploader()),
                tags,
                dateiDTO.getUploaddatum(),
                dateiDTO.getVeroeffentlichungsdatum(),
                dateiDTO.getDateigroesse(),
                dateiDTO.getDateityp(),
                dateiDTO.getKategorie());
    }

    private Tag loadTag(final TagDTO tagDTO) {
        return new Tag(tagDTO.getId(), tagDTO.getText());
    }

    public User loadUser(final UserDTO userDTO) {

        if (userDTO == null) {
            return new User(-1L, "", "", "", new HashMap<>());
        }

        return new User(
                userDTO.getId(),
                userDTO.getVorname(),
                userDTO.getNachname(),
                userDTO.getKeycloakname(),
                convertHashMapGruppeDTOtoGruppe(userDTO));
    }

    public HashMap<Gruppe, Boolean> convertHashMapGruppeDTOtoGruppe(final UserDTO userDTO) {
        HashMap<Gruppe, Boolean> belegungUndRechte = new HashMap<>();
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            belegungUndRechte.put(
                    loadGruppe(gruppeDTO),
                    userDTO.getBelegungUndRechte().get(gruppeDTO));
        }
        return belegungUndRechte;
    }

    private Gruppe loadGruppe(final GruppeDTO gruppeDTO) {
        return new Gruppe(gruppeDTO.getId(), gruppeDTO.getName(), dateienDerGruppe(gruppeDTO));
    }

    private User loadUploader(final UserDTO dto) {
        return new User(
                dto.getId(),
                dto.getVorname(),
                dto.getNachname(),
                dto.getKeycloakname(),
                new HashMap<>());
    }

    public List<Datei> dateienDerGruppe(final GruppeDTO gruppeDTO) {
        return gruppeDTO.getDateien()
                .stream()
                .map(this::loadDatei)
                .collect(Collectors.toList());

    }

    public List<Gruppe> getAlleGruppenByUser(final KeycloakAuthenticationToken token) {
        User user = createUserByToken(token);
        return user.getAllGruppen();
    }

    public List<Datei> getAlleDateienByGruppe(final Long gruppeId,
                                              final KeycloakAuthenticationToken token) {
        User user = createUserByToken(token);
        return user.getGruppeById(gruppeId).getDateien();
    }

    public Set<String> getAlleTagsByUser(final KeycloakAuthenticationToken token) {
        User user = createUserByToken(token);
        List<Gruppe> groups = user.getAllGruppen();
        Set<String> tags = new HashSet<>();
        for (Gruppe gruppe : groups) {
            gruppe.getDateien().forEach(datei -> datei.getTags()
                    .forEach(tag -> tags.add(tag.getText())));
        }
        return tags;
    }

    public Set<String> getAlleTagsByGruppe(final Long gruppeId,
                                           final KeycloakAuthenticationToken token) {
        User user = createUserByToken(token);
        List<Datei> dateienListe = user.getGruppeById(gruppeId).getDateien();
        Set<String> tags = new HashSet<>();
        dateienListe.forEach(datei -> datei.getTags()
                .forEach(tag -> tags.add(tag.getText())));
        return tags;
    }


    public Set<String> getAlleUploaderByUser(final KeycloakAuthenticationToken token) {
        User user = createUserByToken(token);
        List<Gruppe> groups = user.getAllGruppen();
        Set<String> uploader = new HashSet<String>();
        for (Gruppe gruppe : groups) {
            uploader.addAll(gruppe.getDateien()
                    .stream()
                    .map(datei -> datei.getUploader().getNachname())
                    .collect(Collectors.toSet()));
        }
        return uploader;
    }

    public Set<String> getAlleUploaderByGruppe(final Long gruppeId,
                                               final KeycloakAuthenticationToken token) {
        User user = createUserByToken(token);
        return user.getGruppeById(gruppeId).getDateien()
                .stream()
                .map(datei -> datei.getUploader().getNachname())
                .collect(Collectors.toSet());
    }

    public void suchen(final Suche sucheArg) {
        this.suche = sucheArg;
    }

    public List<Datei> getSuchergebnisse(final KeycloakAuthenticationToken token) {
        List<Datei> zuFiltern;
        if (suche.getGruppenId() != null) {
            zuFiltern = getAlleDateienByGruppe(suche.getGruppenId(), token);
        } else {
            User user = createUserByToken(token);
            zuFiltern = getAlleDateienByUser(user);
        }
        return suchService.starteSuche(suche, zuFiltern);
    }

    public Set<String> getKategorienFromSuche(final List<Datei> dateien) {
        Set<String> kategorien = new HashSet<>();
        dateien.forEach(datei -> kategorien.add(datei.getKategorie()));
        return kategorien;
    }

    public Set<String> getKategorienByGruppe(final Long gruppeId, final KeycloakAuthenticationToken token) {
        List<Datei> dateien = getAlleDateienByGruppe(gruppeId, token);
        Set<String> kategorien = new HashSet<>();
        dateien.forEach(datei -> kategorien.add(datei.getKategorie()));
        return kategorien;
    }

    public Boolean isSortedByKategorie() {
        return "Kategorie".equals(suche.getSortierKriterium());
    }

    public Set<String> getAlleDateiTypenByUser(final KeycloakAuthenticationToken token) {
        User user = createUserByToken(token);
        List<Gruppe> groups = user.getAllGruppen();
        Set<String> dateiTypen = new HashSet<String>();
        for (Gruppe gruppe : groups) {
            dateiTypen.addAll(gruppe.getDateien()
                    .stream()
                    .map(Datei::getDateityp)
                    .collect(Collectors.toSet()));
        }
        return dateiTypen;
    }

    public Set<String> getAlleDateiTypenByGruppe(final Long gruppeId,
                                                 final KeycloakAuthenticationToken token) {
        User user = createUserByToken(token);
        return user.getGruppeById(gruppeId).getDateien()
                .stream()
                .map(Datei::getDateityp)
                .collect(Collectors.toSet());
    }

    public Account getAccountFromKeycloak(final KeycloakAuthenticationToken token) {
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        return new Account(
                principal.getName(),
                principal.getKeycloakSecurityContext().getIdToken().getEmail(),
                null,
                token.getAccount().getRoles());
    }

    private User createUserByToken(final KeycloakAuthenticationToken token) {
        Account account = getAccountFromKeycloak(token);

        try {
            return loadUser(repository.findUserByKeycloakname(account.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
            return new User(-1L, "", "", "", new HashMap<>());
        }
    }

    private List<Datei> getAlleDateienByUser(final User user) {
        List<Datei> alleDateien = new ArrayList<>();
        user.getAllGruppen().forEach(gruppe -> alleDateien.addAll(gruppe.getDateien()));
        return alleDateien;
    }

    public long saveDatei(final Datei datei, final Gruppe gruppe) throws SQLException {
        if (datei == null || gruppe == null) {
            throw new IllegalArgumentException();
        }
        // create GruppeDTO and UserDTO only with Id as parameter because Id is the only parameter which is necessary
        // for saving the file
        GruppeDTO groupDTO = new GruppeDTO(gruppe.getId(), null,
                null, null);
        UserDTO userDTO = new UserDTO(datei.getUploader().getId(), null, null,
                null, null);

        DateiDTO dateiDTO = new DateiDTO(datei.getName(), userDTO, tagsToTagDTOs(datei.getTags()),
                datei.getUploaddatum(), datei.getVeroeffentlichungsdatum(), datei.getDateigroesse(),
                datei.getDateityp(), groupDTO, null);
        return repository.saveDatei(dateiDTO);
    }

    private ArrayList<TagDTO> tagsToTagDTOs(final List<Tag> tags) {
        ArrayList<TagDTO> tagDTOs = new ArrayList<>();
        if (tags == null || tags.isEmpty()) {
            return tagDTOs;
        }
        for (Tag tag : tags) {
            tagDTOs.add(new TagDTO(tag.getText()));
        }
        return tagDTOs;
    }
}
