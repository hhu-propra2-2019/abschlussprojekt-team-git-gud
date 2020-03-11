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

    public Datei load(final DateiDTO dto) {
        List<Tag> tags = dto.getTagDTOs().stream()
                .map(this::load)
                .collect(Collectors.toList());
        return new Datei(
                dto.getId(),
                dto.getName(),
                dto.getPfad(),
                load(dto.getUploader()),
                tags,
                dto.getUploaddatum(),
                dto.getVeroeffentlichungsdatum(),
                dto.getDateigroesse(),
                dto.getDateityp());
    }

    public Tag load(final TagDTO dto) {
        return new Tag(dto.getId(), dto.getText());
    }

    public User load(final UserDTO dto) {
        HashMap<Gruppe, Boolean> belegungUndRechte = new HashMap<>();
        for (GruppeDTO gruppeDTO : dto.getBelegungUndRechte().keySet()) {
            belegungUndRechte.put(
                    load(gruppeDTO),
                    dto.getBelegungUndRechte().get(gruppeDTO));
        }

        return new User(
                dto.getId(),
                dto.getVorname(),
                dto.getNachname(),
                dto.getKeycloakname(),
                belegungUndRechte);
    }

    public Gruppe load(final GruppeDTO dto) {
        List<Datei> zugehoerigeDateien =
                dto.getDateien()
                        .stream()
                        .map(this::load)
                        .collect(Collectors.toList());
        return new Gruppe(dto.getId(), dto.getName(), zugehoerigeDateien);
    }

    public List<Gruppe> getAlleGruppenByUser(final String name) {
        if (users.findByKeycloakname(name) == null) {
            return new ArrayList<>();
        }
        User user = load(users.findByKeycloakname(name));
        return user.getAllGruppen();
    }

    public List<Datei> getAlleDateienByGruppe(final Gruppe gruppe) {
        return gruppe.getDateien();
    }

    public Set<String> getAlleTagsByUser(final String name) {
        User user = load(users.findByKeycloakname(name));
        List<Gruppe> groups = user.getAllGruppen();
        Set<String> tags = new HashSet<>();
        for (Gruppe gruppe : groups) {
            tags.addAll(gruppe.getDateien()
                    .stream()
                    .map(Datei::getName)
                    .collect(Collectors.toSet()));
        }
        return tags;
    }

    public Set<String> getAlleTagsByGruppe(final Gruppe gruppe) {
        return gruppe.getDateien()
                .stream().map(Datei::getName)
                .collect(Collectors.toSet());
    }

    public Set<String> getAlleUploaderByUser(final String name) {
        User user = load(users.findByKeycloakname(name));
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

    public Set<String> getAlleDateiTypenByUser(final String name) {
        User user = load(users.findByKeycloakname(name));
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
}
