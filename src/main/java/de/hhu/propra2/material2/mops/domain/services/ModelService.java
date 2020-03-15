package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.database.TagRepository;
import de.hhu.propra2.material2.mops.database.entities.Datei;
import de.hhu.propra2.material2.mops.database.entities.Gruppe;
import de.hhu.propra2.material2.mops.database.entities.Tag;
import de.hhu.propra2.material2.mops.database.entities.User;
import de.hhu.propra2.material2.mops.database.DateiRepository;
import de.hhu.propra2.material2.mops.database.GruppeRepository;
import de.hhu.propra2.material2.mops.database.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class ModelService implements IModelService {

    private final DateiRepository dateiRepository;
    private final GruppeRepository gruppeRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;


    /**
     * Constructor of ModelService.
     *
     * @param dateiRepo   DateiRepository
     * @param gruppenRepo GruppenRepository
     * @param userRepo    UserRepository
     */
    public ModelService(final DateiRepository dateiRepo,
                        final GruppeRepository gruppenRepo,
                        final UserRepository userRepo,
                        final TagRepository tagRepo) {
        this.dateiRepository = dateiRepo;
        this.gruppeRepository = gruppenRepo;
        this.userRepository = userRepo;
        this.tagRepository = tagRepo;
    }

    public Datei loadDatei(final long dateiId) {
        Iterable<Datei> data = dateiRepository.findAll();
        for (Datei datei: data) {
            if (datei.getDateiID() == dateiId) {
                return datei;
            }
        }
        return null;
    }

    public Tag loadTag(final long tagId) {
        Iterable<Tag> data = tagRepository.findAll();
        for (Tag tag: data) {
            if (tag.getTagID() == tagId) {
                return tag;
            }
        }
        return null;
    }

    public User loadUser(final String keyCloakName) {
        //TODO load Belegungsrechte
        Iterable<User> data = userRepository.findAll();
        for (User user: data) {
            if (user.getKeyCloakName() == keyCloakName) {
                return user;
            }
        }
        return null;
    }

    public Gruppe loadGruppe(final long gruppeId) {
        Iterable<Gruppe> gruppen = gruppeRepository.findAll();
        for (Gruppe gruppe: gruppen) {
            if (gruppe.getGruppeID() == gruppeId) {
                return gruppe;
            }
        }
        return null;
    }

    public List<Gruppe> getAlleGruppenByUser(final User user) {
        return user.getGruppen();
    }

    public List<Datei> getAlleDateienByGruppe(final Gruppe gruppe) {
        return gruppe.getDateien();
    }

    public List getAlleTagsByUser(final User user) {
        List<Gruppe> groups = user.getGruppen();
        List tags = new ArrayList();
        for (Gruppe gruppe : groups) {
            gruppe.getDateien().forEach(datei -> datei.getTags()
                    .forEach(tag -> tags.add(tag.getTagname())));
        }
        return tags;
    }

    public Set<String> getAlleTagsByGruppe(final Gruppe gruppe) {
        List<Datei> dateienListe = gruppe.getDateien();
        Set<String> tags = new HashSet<>();
        dateienListe.forEach(datei -> datei.getTags()
                .forEach(tag -> tags.add(tag.getTagname())));
        return tags;
    }

    public List<String> getAlleUploaderByUser(final User user) {
        List<Gruppe> groups = user.getGruppen();
        List<String> uploader = new ArrayList<>();
        for (Gruppe gruppe : groups) {
            uploader.addAll(gruppe.getDateien()
                    .stream()
                    .map(datei -> datei.getUploader().getKeyCloakName())
                    .collect(Collectors.toSet()));
        }
        return uploader;
    }

    public Set<String> getAlleUploaderByGruppe(final Gruppe gruppe) {
        return gruppe.getDateien()
                .stream()
                .map(datei -> datei.getUploader().getKeyCloakName())
                .collect(Collectors.toSet());
    }

    public List<String> getAlleDateiTypenByUser(final User user) {
        List<Gruppe> groups = user.getGruppen();
        List<String> dateiTypen = new ArrayList<>();
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
        Gruppe gruppe = loadGruppe(id);
        return gruppe.getDateien();
    }

    public User getUserByKeyCloackName(final String name) {
        return loadUser(name);
    }
}
