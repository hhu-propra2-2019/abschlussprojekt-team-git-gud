package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.Repository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public final class ModelService implements IModelService {


    private final Repository repository;

    /**
     * Constructor of ModelService.
     */
    public ModelService(final Repository repositoryArg) {
        repository = repositoryArg;
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

    public List<Gruppe> getAlleGruppenByUser(final User user) {
        return user.getAllGruppen();
    }

    public List<Datei> getAlleDateienByGruppe(final Gruppe gruppe) {
        return gruppe.getDateien();
    }

    public Set<String> getAlleTagsByUser(final User user) {
        List<Gruppe> groups = user.getAllGruppen();
        Set<String> tags = new HashSet<>();
        for (Gruppe gruppe : groups) {
            gruppe.getDateien().forEach(datei -> datei.getTags()
                    .forEach(tag -> tags.add(tag.getText())));
        }
        return tags;
    }

    public Set<String> getAlleTagsByGruppe(final Gruppe gruppe) {
        List<Datei> dateienListe = gruppe.getDateien();
        Set<String> tags = new HashSet<>();
        dateienListe.forEach(datei -> datei.getTags()
                .forEach(tag -> tags.add(tag.getText())));
        return tags;
    }


    public Set<String> getAlleUploaderByUser(final User user) {
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

    public Set<String> getAlleUploaderByGruppe(final Gruppe gruppe) {
        return gruppe.getDateien()
                .stream()
                .map(datei -> datei.getUploader().getNachname())
                .collect(Collectors.toSet());
    }


    public Set<String> getAlleDateiTypenByUser(final User user) {
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

    public Set<String> getAlleDateiTypenByGruppe(final Gruppe gruppe) {
        return gruppe.getDateien()
                .stream()
                .map(Datei::getDateityp)
                .collect(Collectors.toSet());
    }

    public List<Datei> getAlleDateienByGruppeId(final Long id) {
        return null;
    }

    public User getUserByKeyCloakName(final String name) {
        try {
            return loadUser(repository.findUserByKeycloakname(name));
        } catch (SQLException e) {
            e.printStackTrace();
            return new User(-1L, "", "", "", new HashMap<>());
        }
    }

    @Override
    public Set<String> getAlleKategorienByFiles(final List<Datei> dateien) {
        Set<String> kategorien = new HashSet<>();
        dateien.forEach(datei -> kategorien.add(datei.getKategorie()));
        return kategorien;
    }
}
