package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.database.DateiRepository;
import de.hhu.propra2.material2.mops.database.GruppeRepository;
import de.hhu.propra2.material2.mops.database.UserRepository;
import de.hhu.propra2.material2.mops.database.entities.GruppeDAO;
import de.hhu.propra2.material2.mops.database.entities.DateiDAO;
import de.hhu.propra2.material2.mops.database.entities.TagDAO;
import de.hhu.propra2.material2.mops.database.entities.UserDAO;
import de.hhu.propra2.material2.mops.database.entities.GruppenbelegungDAO;
import de.hhu.propra2.material2.mops.domain.IModelService;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.springframework.stereotype.Service;

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

    public Datei loadDatei(final DateiDAO dao) {
        List<Tag> tags = dao.getTagDAOS().stream()
                .map(this::load)
                .collect(Collectors.toList());
        return new Datei(
                dao.getDateiID(),
                dao.getName(),
                dao.getPfad(),
                loadUser(dao.getUploader()),
                tags,
                dao.getUploaddatum(),
                dao.getVeroeffentlichungsdatum(),
                dao.getDateigroesse(),
                dao.getDateityp());
    }

    public Tag load(final TagDAO dao) {
        return new Tag(dao.getTagID(), dao.getTagname());
    }

    public User loadUser(final UserDAO dao) {
        HashMap<Gruppe, Boolean> belegungUndRechte = new HashMap<>();
        for (GruppenbelegungDAO belegung : dao.getGruppen()) {
            belegungUndRechte.put(load(belegung.getGruppe()), belegung.isBerechtigung());
        }

        return new User(
                dao.getUserID(),
                dao.getVorname(),
                dao.getNachname(),
                dao.getKeyCloakName(),
                belegungUndRechte);
    }

    public Gruppe load(final GruppeDAO dao) {
        List<de.hhu.propra2.material2.mops.domain.models.Datei> zugehoerigeDateien =
                dao.getDateien()
                        .stream()
                        .map(this::loadDatei)
                        .collect(Collectors.toList());
        return new Gruppe(dao.getGruppeID(), dao.getTitel(), zugehoerigeDateien);
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
        List<de.hhu.propra2.material2.mops.domain.models.Datei> dateienListe = gruppe.getDateien();
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
                    .map(de.hhu.propra2.material2.mops.domain.models.Datei::getDateityp)
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

    public List<de.hhu.propra2.material2.mops.domain.models.Datei> getAlleDateienByGruppeId(final Long id) {
        Gruppe gruppe = load(gruppen.findById(id).get());
        return gruppe.getDateien();
    }

    public User getUserByKeyCloackName(final String name) {
        return loadUser(users.findByKeyCloakName(name));
    }
}
