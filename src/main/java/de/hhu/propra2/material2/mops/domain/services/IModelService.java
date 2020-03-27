package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.FileNotPublishedYetException;
import de.hhu.propra2.material2.mops.Exceptions.NoAccessPermissionException;
import de.hhu.propra2.material2.mops.Exceptions.NoDownloadPermissionException;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.security.Account;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IModelService {

    List<Gruppe> getAlleGruppenByUser(KeycloakAuthenticationToken token);

    List<Gruppe> getAlleUploadGruppenByUser(KeycloakAuthenticationToken token);

    List<Datei> getAlleDateienByGruppe(String gruppeId, KeycloakAuthenticationToken token);

    Gruppe getGruppeByUserAndGroupID(String gruppeId, KeycloakAuthenticationToken token);

    Set<String> getAlleTagsByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleTagsByGruppe(String gruppeId, KeycloakAuthenticationToken token);

    Set<String> getAlleDateiTypenByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleDateiTypenByGruppe(String gruppeId, KeycloakAuthenticationToken token);

    Set<String> getAlleUploaderByUser(KeycloakAuthenticationToken token);

    Set<String> getAlleUploaderByGruppe(String gruppeId, KeycloakAuthenticationToken token);

    void suchen(Suche suche);

    List<Datei> getSuchergebnisse(KeycloakAuthenticationToken token);

    Set<String> getKategorienFromSuche(List<Datei> dateien);

    Set<String> getKategorienByGruppe(String gruppeId, KeycloakAuthenticationToken token);

    Boolean isSortedByKategorie();

    Account getAccountFromKeycloak(KeycloakAuthenticationToken token);

    Datei getDateiById(long dateiId, KeycloakAuthenticationToken token)
            throws SQLException, NoAccessPermissionException;

    Boolean userHasEditPermissionForFile(Long dateiId, KeycloakAuthenticationToken token)
            throws NoDownloadPermissionException, SQLException, NoDownloadPermissionException;

    Boolean filesIsPublished(Long fileId) throws SQLException, FileNotPublishedYetException;
}
