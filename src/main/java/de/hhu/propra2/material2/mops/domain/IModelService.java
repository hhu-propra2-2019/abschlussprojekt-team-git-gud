package de.hhu.propra2.material2.mops.domain;


import de.hhu.propra2.material2.mops.database.entities.Datei;
import de.hhu.propra2.material2.mops.database.entities.Gruppe;
import de.hhu.propra2.material2.mops.database.entities.User;

import java.util.List;
import java.util.Set;

public interface IModelService {

    List<Gruppe> getAlleGruppenByUser(User user);
    List<Datei> getAlleDateienByGruppe(Gruppe gruppe);
    List getAlleTagsByUser(User user);
    Set<String> getAlleTagsByGruppe(Gruppe gruppe);
    Set<String> getAlleDateiTypenByGruppe(Gruppe gruppe);
    List<String> getAlleUploaderByUser(User user);
    Set<String> getAlleUploaderByGruppe(Gruppe gruppe);
    User getUserByKeyCloackName(String name);
}
