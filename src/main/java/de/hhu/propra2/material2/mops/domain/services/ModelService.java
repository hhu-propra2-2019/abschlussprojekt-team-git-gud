package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.DateiRepository;
import de.hhu.propra2.material2.mops.Database.GruppeRepository;
import de.hhu.propra2.material2.mops.Database.TagRepository;
import de.hhu.propra2.material2.mops.Database.UserRepository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class ModelService implements IModelService {
    private final DateiRepository dateien;
    private final GruppeRepository gruppen;
    private final UserRepository users;
    private final TagRepository tags;

    /**
     * Constructor of ModelService.
     *
     * @param dateiRepo   DateiRepository
     * @param gruppenRepo GruppeRepository
     * @param userRepo    UserRepository
     * @param tagRepo     TagRepository
     */
    public ModelService(final DateiRepository dateiRepo,
                        final GruppeRepository gruppenRepo,
                        final UserRepository userRepo,
                        final TagRepository tagRepo) {
        this.dateien = dateiRepo;
        this.gruppen = gruppenRepo;
        this.users = userRepo;
        this.tags = tagRepo;
    }

    public Datei loadDatei(final DateiDTO dto) {
        List<Tag> tagList = dto.getTagDTOs().stream()
                .map(this::load)
                .collect(Collectors.toList());
        return new Datei(
                dto.getId(),
                dto.getName(),
                dto.getPfad(),
                loadUser(dto.getUploader()),
                tagList,
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
        User user = loadUser(users.findByKeycloakname(name));
        List<Gruppe> groups = user.getAllGruppen();
        Set<String> tagList = new HashSet<>();
        for (Gruppe gruppe : groups) {
            gruppe.getDateien().forEach(datei -> datei.getTags()
                    .forEach(tag -> tagList.add(tag.getText())));
        }
        return tagList;
    }

    public Set<String> getAlleTagsByGruppe(final Gruppe gruppe) {
        List<Datei> dateienListe = gruppe.getDateien();
        Set<String> tagList = new HashSet<>();
        dateienListe.forEach(datei -> datei.getTags()
                .forEach(tag -> tagList.add(tag.getText())));
        return tagList;
    }

    public Set<String> getAlleUploaderByUser(final String name) {
        User user = loadUser(users.findByKeycloakname(name));
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
        User user = loadUser(users.findByKeycloakname(name));
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

    private DateiDTO saveDateiWithoutTags(
            final Datei datei, final GruppeDTO gruppeDTO,
            final UserDTO userDTO) {
        return saveDatei(datei, gruppeDTO, userDTO, null);
    }

    private DateiDTO saveDatei(final Datei datei, final GruppeDTO gruppeDTO,
                               final UserDTO userDTO,
                               final List<TagDTO> tagDTOs) {
        DateiDTO dateiDTO = new DateiDTO(datei.getName(),
                datei.getPfad(), userDTO, tagDTOs, datei.getUploaddatum(),
                datei.getVeroeffentlichungsdatum(), datei.getDateigroesse(),
                datei.getDateityp(), gruppeDTO);
        return dateien.save(dateiDTO);
    }

    public void saveDatei(final Datei datei, final Gruppe gruppe)
            throws Exception {
        if (datei == null || gruppe == null) {
            throw new IllegalArgumentException();
        }

        Optional<GruppeDTO> optionalGruppeDTO =
                gruppen.findById(gruppe.getId());
        if (!optionalGruppeDTO.isPresent()) {
            throw new Exception("no group found");
        }
        final GruppeDTO gruppeDTO = optionalGruppeDTO.get();

        Optional<UserDTO> optionalUserDTO =
                users.findById(datei.getUploader().getId());
        if (!optionalGruppeDTO.isPresent()) {
            throw new Exception("no user found");
        }
        final UserDTO userDTO = optionalUserDTO.get();

        DateiDTO dateiDTO = saveDateiWithoutTags(datei, gruppeDTO, userDTO);

        Iterable<TagDTO> savedTags = this.saveNewTags(datei.getTags(), dateiDTO);
        List<TagDTO> tagDTOs = new ArrayList<>();
        savedTags.forEach(tagDTOs::add);

        dateiDTO = saveDatei(datei, gruppeDTO, userDTO, tagDTOs);
    }

    private Iterable<TagDTO> saveNewTags(final List<Tag> potentiallyNewTags,
                                         final DateiDTO dateiDTO) {
        if (potentiallyNewTags == null || potentiallyNewTags.isEmpty()) {
            return null;
        }

        Iterable<TagDTO> tagDTOs =
                tags.findAllByText(potentiallyNewTags.stream()
                        .map(Tag::getText)
                        .collect(Collectors.toList()));

        List<TagDTO> newTagDTOs = new ArrayList<>();
        for (Tag potentiallyNewTag : potentiallyNewTags) {
            boolean exists = false;
            for (TagDTO tagDTO : tagDTOs) {
                if (potentiallyNewTag.getText().equals(tagDTO.getText())) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                newTagDTOs.add(new TagDTO(-1, potentiallyNewTag.getText(),
                        Collections.singletonList(dateiDTO)));
            }
        }

        for (TagDTO tagDTO : tagDTOs) {
            tagDTO.getDateien().add(dateiDTO);
        }

        newTagDTOs.addAll((Collection<? extends TagDTO>) tagDTOs);
        return tags.saveAll(newTagDTOs);
    }
}
