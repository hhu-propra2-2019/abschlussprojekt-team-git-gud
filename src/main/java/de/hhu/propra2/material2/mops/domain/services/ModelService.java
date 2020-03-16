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

import java.time.LocalDate;
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

    public Datei loadDatei(final DateiDTO dto) {
        List<Tag> tags = dto.getTagDTOs().stream()
                .map(this::load)
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

    public Tag load(final TagDTO dto) {
        return new Tag(dto.getId(), dto.getText());
    }

    public User loadUser(final UserDTO dto) {
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
                        .map(this::loadDatei)
                        .collect(Collectors.toList());
        return new Gruppe(dto.getId(), dto.getName(), zugehoerigeDateien);
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
        Gruppe gruppe = load(gruppen.findById(id).get());
        return gruppe.getDateien();
    }

    public User getUserByKeyCloackName(final String name) {
        return loadUser(users.findByKeycloakname(name));
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public User createDummyUser() {
        HashMap mapUser = new HashMap();
        HashMap mapUploader = new HashMap();


        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "Cool"));
        tags.add(new Tag(2, "Auch Cool"));

        List<Tag> tags2 = new ArrayList<>();
        tags.add(new Tag(1, "Nice"));
        tags.add(new Tag(2, "Auch Nice"));

        LocalDate uploadDatum = LocalDate.of(2010, 10, 10);
        LocalDate veroeffentlichung = LocalDate.of(2010, 10, 10);

        LocalDate uploadDatum2 = LocalDate.of(2020, 10, 10);
        LocalDate veroeffentlichung2 = LocalDate.of(2020, 10, 10);

        User uploader = new User(2, "Jens", "Bendisposto", "Jeben", mapUploader);
        User uploader2 = new User(2, "Oleg", "BesterMann", "Olbes", mapUploader);
        List<Datei> data = new ArrayList<>();
        data.add(new Datei(1, "Blatt 1", "", uploader, tags, uploadDatum, veroeffentlichung, 100, "PDF"));

        List<Datei> data2 = new ArrayList<>();
        data2.add(new Datei(1, "Blatt 12", "", uploader2, tags2, uploadDatum2, veroeffentlichung2, 100, "jpeg"));
        data.add(new Datei(1, "Blatt 12", "", uploader2, tags2, uploadDatum2, veroeffentlichung2, 100, "jpeg"));


        Gruppe gruppe1 = new Gruppe(1, "ProPra", data);
        Gruppe gruppe2 = new Gruppe(2, "BWL", data2);
        mapUser.put(gruppe1, false);
        mapUser.put(gruppe2, true);

        User user = new User(1, "Hans", "Müller", "Hamue", mapUser);
        return user;
    }
}
