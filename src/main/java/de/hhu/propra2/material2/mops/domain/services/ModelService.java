package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.DateiRepository;
import de.hhu.propra2.material2.mops.Database.GruppeRepository;
import de.hhu.propra2.material2.mops.Database.UserRepository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class ModelService implements IModelService {
    private final DateiRepository dateien;
    private final GruppeRepository gruppen;
    private final UserRepository users;

    /**
     * Constructor of ModelService.
     *
     * @param dateiRepo   DateiRepository
     * @param gruppenRepo GruppenRepository
     * @param userRepo    UserRepository
     */
    public ModelService(final DateiRepository dateiRepo,
                        final GruppeRepository gruppenRepo,
                        final UserRepository userRepo) {
        this.dateien = dateiRepo;
        this.gruppen = gruppenRepo;
        this.users = userRepo;
    }

    private Datei loadDatei(final DateiDTO dto) {
        List<Tag> tags = dto.getTagDTOs().stream()
                .map(this::loadTag)
                .collect(Collectors.toList());
        return new Datei(
                dto.getId(),
                dto.getName(),
                dto.getPfad(),
                loadUser(dto.getUploader()),
                tags,
                dto.getUploaddatum(),
                dto.getVeroeffentlichungsdatum(),
                dto.getDateigroesse(),
                dto.getDateityp());
    }

    private Tag loadTag(final TagDTO tagDTO) {
        return new Tag(tagDTO.getId(), tagDTO.getText());
    }

    public User loadUser(final UserDTO userDTO) {
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

    private Gruppe loadGruppe(final GruppeDTO dto) {
        List<Datei> zugehoerigeDateien =
                dto.getDateien()
                        .stream()
                        .map(this::loadDatei)
                        .collect(Collectors.toList());
        return new Gruppe(dto.getId(), dto.getName(), zugehoerigeDateien);
    }

    public List<Gruppe> getAlleGruppenByUser(final String name) {
        if (users.findByKeycloakname(name) == null) {
            return new ArrayList<>();
        }
        User user = loadUser(users.findByKeycloakname(name));
        return user.getAllGruppen();
    }

    public List<Datei> getAlleDateienByGruppe(final Gruppe gruppe) {
        return gruppe.getDateien();
    }

    public Set<String> getAlleTagsByUser(final String name) {
        if (users.findByKeycloakname(name) == null) {
            return new HashSet<>();
        }
        User user = loadUser(users.findByKeycloakname(name));
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

    public Set<String> getAlleUploaderByUser(final String name) {
        if (users.findByKeycloakname(name) == null) {
            return new HashSet<>();
        }
        User user = loadUser(users.findByKeycloakname(name));
        List<Gruppe> groups = user.getAllGruppen();
        Set<String> uploader = new HashSet<>();
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

    public Set<String> getAlleDateiTypenByUser(final String name) {
        if (users.findByKeycloakname(name) == null) {
            return new HashSet<>();
        }
        User user = loadUser(users.findByKeycloakname(name));
        List<Gruppe> groups = user.getAllGruppen();
        Set<String> dateiTypen = new HashSet<>();
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
}
