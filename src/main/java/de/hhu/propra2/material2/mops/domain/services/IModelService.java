package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;

import java.util.List;
import java.util.Set;

public interface IModelService {

    List<Gruppe> getAlleGruppenByUser(String name);
    List<Datei> getAlleDateienByGruppe(Gruppe gruppe);
    Set<String> getAlleTagsByUser(String name);
    Set<String> getAlleTagsByGruppe(Gruppe gruppe);
    Set<String> getAlleDateiTypenByUser(String name);
    Set<String> getAlleDateiTypenByGruppe(Gruppe gruppe);
    Set<String> getAlleUploaderByUser(String name);
    Set<String> getAlleUploaderByGruppe(Gruppe gruppe);
}
