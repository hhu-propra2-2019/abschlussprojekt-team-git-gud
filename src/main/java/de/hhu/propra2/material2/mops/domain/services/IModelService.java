package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.domain.models.User;
import de.hhu.propra2.material2.mops.security.Account;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import java.util.List;
import java.util.Set;

public interface IModelService {

    List<Gruppe> getAlleGruppenByUser(KeycloakAuthenticationToken token);

    List<Datei> getAlleDateienByGruppe(Long gruppeId, KeycloakAuthenticationToken token);

    Set<String> getAlleTagsByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleTagsByGruppe(Long gruppeId);

    Set<String> getAlleDateiTypenByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleDateiTypenByGruppe(Long gruppeId, KeycloakAuthenticationToken token);

    Set<String> getAlleUploaderByUser(User user);

    Set<String> getAlleUploaderByGruppe(Long gruppeId, KeycloakAuthenticationToken token);

    User getUserByKeyCloakName(String name);

    Set<String> getAlleKategorienByFiles(List<Datei> dateien);

    void suchen(Suche suche);

    List<Datei> getSuchergebnisse(KeycloakAuthenticationToken token);

    List<String> getKategorienFromSuche(KeycloakAuthenticationToken token);

    List<String> getKategorienByGruppe(Long gruppeId, KeycloakAuthenticationToken token);

    Boolean isSortedByKategorie(KeycloakAuthenticationToken token);

    Account getAccountFromKeycloak(KeycloakAuthenticationToken token);
}
