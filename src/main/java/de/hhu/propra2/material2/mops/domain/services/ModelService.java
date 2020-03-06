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
import java.util.stream.Collectors;

@Service
public class ModelService {

    private final DateiRepository dateien;
    private final GruppeRepository gruppen;
    private final UserRepository users;

    public ModelService(DateiRepository dateien, GruppeRepository gruppen, UserRepository users) {
        this.dateien = dateien;
        this.gruppen = gruppen;
        this.users = users;
    }

    public Datei load(DateiDTO dto) {
        //List<Tag> tags = dto.getTagDTOS().stream().map(this::load).collect(Collectors.toList());
        List<Tag> tags = new ArrayList<>();
        dto.getTagDTOs().forEach(tagDTO -> tags.add(load(tagDTO)));
        return new Datei(dto.getId(), dto.getName(), dto.getPfad(), load(dto.getUploader()), tags, dto.getUploaddatum(), dto.getVeroeffentlichungsdatum(), dto.getDateigroesse(), dto.getDateityp());
    }

    public Tag load(TagDTO dto) {
        return new Tag(dto.getId(), dto.getText());
    }

    public User load(UserDTO dto) {
        HashMap<Gruppe, Boolean> belegungUndRechte = new HashMap<>();
        for (GruppeDTO gruppeDTO : dto.getBelegungUndRechte().keySet()) {
            belegungUndRechte.put(load(gruppeDTO), dto.getBelegungUndRechte().get(gruppeDTO));
        }

        return new User(dto.getId(), dto.getVorname(), dto.getNachname(), dto.getKeycloakname(), belegungUndRechte);
    }

    public Gruppe load(GruppeDTO dto) {
        List<Datei> zugehoerigeDateien = dto.getDateien().stream().map(this::load).collect(Collectors.toList());
        return new Gruppe(dto.getId(), dto.getName(), zugehoerigeDateien);
    }

    public List<Gruppe> getAlleGruppenByUser(Long id) {
        User user = load(users.findById(id).get());
        return user.getAllGruppen();
    }
}
