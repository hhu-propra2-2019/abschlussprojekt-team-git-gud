package de.hhu.propra2.material2.mops.domain;


import de.hhu.propra2.material2.mops.database.entities.DateiDAO;
import de.hhu.propra2.material2.mops.database.entities.GruppeDAO;
import de.hhu.propra2.material2.mops.database.entities.UserDAO;

import java.util.List;
import java.util.Set;

public interface IModelService {

    List<GruppeDAO> getAlleGruppenByUser(UserDAO userDAO);
    List<DateiDAO> getAlleDateienByGruppe(GruppeDAO gruppeDAO);
    List getAlleTagsByUser(UserDAO userDAO);
    Set<String> getAlleTagsByGruppe(GruppeDAO gruppeDAO);
    Set<String> getAlleDateiTypenByGruppe(GruppeDAO gruppeDAO);
    List<String> getAlleUploaderByUser(UserDAO userDAO);
    Set<String> getAlleUploaderByGruppe(GruppeDAO gruppeDAO);
    UserDAO getUserByKeyCloackName(String name);
}
