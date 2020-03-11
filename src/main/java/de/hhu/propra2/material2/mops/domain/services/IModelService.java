package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;

import java.util.List;
import java.util.Set;

public interface IModelService {

    List<Gruppe> getAlleGruppenByUser(String name);
    List<Datei> getAlleDateienByGruppe(Gruppe gruppe);
    List<Tag> getAlleTagsByUser(User user);
    List<Tag> getAlleTagsByGruppe(Gruppe gruppe);
    Set<String> getAlleDateiTypenByUser(User user);
    Set<String> getAlleDateiTypenByGruppe(Gruppe gruppe);
    Set<String> getAlleUploaderTypenByUser(User user);
    Set<String> getAlleUploaderTypenByGruppe(Gruppe gruppe);
}
