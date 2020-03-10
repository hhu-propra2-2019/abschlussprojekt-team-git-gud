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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class ModelService {
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

    public List<Gruppe> getAlleGruppenByUser(final Long id) {
        Optional<UserDTO> optionalUserDTO = users.findById(id);
        if (optionalUserDTO.isEmpty()) {
            return new ArrayList<>();
        }
        User user = load(optionalUserDTO.get());
        return user.getAllGruppen();
    }

    public List<Datei> getAlleDateienByGruppe(final Gruppe gruppe) {
        return gruppe.getDateien();
    }
}
