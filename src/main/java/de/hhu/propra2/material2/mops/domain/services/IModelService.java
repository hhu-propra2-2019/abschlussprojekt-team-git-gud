package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.User;

import java.util.List;
import java.util.Set;

public interface IModelService {

    List<Gruppe> getAlleGruppenByUser(User user);
    List<Datei> getAlleDateienByGruppe(Gruppe gruppe);
    Set<String> getAlleTagsByUser(User user);
    Set<String> getAlleTagsByGruppe(Gruppe gruppe);
    Set<String> getAlleDateiTypenByUser(User user);
    Set<String> getAlleDateiTypenByGruppe(Gruppe gruppe);
    Set<String> getAlleUploaderByUser(User user);
    Set<String> getAlleUploaderByGruppe(Gruppe gruppe);
    User getUserByKeyCloackName(String name);
    User createDummyUser();
}
