package de.hhu.propra2.material2.mops.domain.services;

import ch.qos.logback.core.subst.Token;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import java.util.List;
import java.util.Set;

public interface IModelService {

    List<Gruppe> getAlleGruppenByUser(KeycloakAuthenticationToken token);

    List<Datei> getAlleDateienByGruppe(Gruppe gruppe);

    Set<String> getAlleTagsByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleTagsByGruppe(Gruppe gruppe);

    Set<String> getAlleDateiTypenByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleDateiTypenByGruppe(Gruppe gruppe);

    Set<String> getAlleUploaderByUser(User user);

    Set<String> getAlleUploaderByGruppe(Gruppe gruppe);

    User getUserByKeyCloakName(String name);

    Set<String> getAlleKategorienByFiles(List<Datei> dateien);

    void suchen(Suche suche);

    List<Datei> getSuchergebnisse(Token token);

}
