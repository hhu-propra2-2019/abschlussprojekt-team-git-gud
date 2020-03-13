package de.hhu.propra2.material2.mops.domain.services;


import de.hhu.propra2.material2.mops.database.entities.Datei;
import de.hhu.propra2.material2.mops.database.entities.Gruppe;
import de.hhu.propra2.material2.mops.database.entities.User;

import java.util.List;
import java.util.Set;

public interface IModelService {

    Set<Gruppe> getAlleGruppenByUser(User user);
    List<Datei> getAlleDateienByGruppe(Gruppe gruppe);
    Set<String> getAlleTagsByUser(User user);
    Set<String> getAlleTagsByGruppe(Gruppe gruppe);
    Set<String> getAlleDateiTypenByUser(User user);
    Set<String> getAlleDateiTypenByGruppe(Gruppe gruppe);
    Set<String> getAlleUploaderByUser(User user);
    Set<String> getAlleUploaderByGruppe(Gruppe gruppe);
    User getUserByKeyCloackName(String name);
}
