package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.security.Account;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import java.util.List;
import java.util.Set;

public interface IModelService {

    List<Gruppe> getAlleGruppenByUser(KeycloakAuthenticationToken token);

    List<Gruppe> getAlleUploadGruppenByUser(KeycloakAuthenticationToken token);

    Gruppe getGruppeByUserAndGroupID(Long gruppeId, KeycloakAuthenticationToken token);

    List<Datei> getAlleDateienByGruppe(Long gruppeId, KeycloakAuthenticationToken token);

    Set<String> getAlleTagsByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleTagsByGruppe(Long gruppeId, KeycloakAuthenticationToken token);

    Set<String> getAlleDateiTypenByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleDateiTypenByGruppe(Long gruppeId, KeycloakAuthenticationToken token);

    Set<String> getAlleUploaderByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleUploaderByGruppe(Long gruppeId, KeycloakAuthenticationToken token);

    void suchen(Suche suche);

    List<Datei> getSuchergebnisse(KeycloakAuthenticationToken token);

    Set<String> getKategorienFromSuche(List<Datei> dateien);

    Set<String> getKategorienByGruppe(Long gruppeId, KeycloakAuthenticationToken token);

    Boolean isSortedByKategorie();

    Account getAccountFromKeycloak(KeycloakAuthenticationToken token);
}
