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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class ModelService {
    private final DateiRepository dateien;
    private final GruppeRepository gruppen;
    private final UserRepository users;
    private final TagRepository tags;

    /**
     * Constructor of ModelService.
     *
     * @param dateiRepo   {@link DateiRepository}
     * @param gruppenRepo {@link GruppeRepository}
     * @param userRepo    {@link UserRepository}
     * @param tagRepo     {@link TagRepository}
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

    public Datei load(final DateiDTO dto) {
        List<Tag> tagList = dto.getTagDTOs().stream()
                .map(this::load)
                .collect(Collectors.toList());
        return new Datei(
                dto.getId(),
                dto.getName(),
                dto.getPfad(),
                load(dto.getUploader()),
                tagList,
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

    public List<Gruppe> getAlleGruppenByUser(final Long id) {
        Optional<UserDTO> optionalUserDTO = users.findById(id);
        if (optionalUserDTO.isEmpty()) {
            return new ArrayList<>();
        }
        User user = load(optionalUserDTO.get());
        return user.getAllGruppen();
    }

    private DateiDTO saveDateiWithoutTags(
            final Datei datei, final GruppeDTO gruppeDTO,
            final UserDTO userDTO) {
        return saveDatei(datei, gruppeDTO, userDTO, null);
    }

    private DateiDTO saveDatei(final Datei datei, final GruppeDTO gruppeDTO,
                               final UserDTO userDTO,
                               final Iterable<TagDTO> tagDTOs) {
        DateiDTO dateiDTO = new DateiDTO(datei.getName(),
                datei.getPfad(), userDTO, null, datei.getUploaddatum(),
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

        Iterable<TagDTO> tagDTOs = this.saveNewTags(datei.getTags(), dateiDTO);

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
