package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.NoDeletePermissionException;
import de.hhu.propra2.material2.mops.Exceptions.ObjectNotInMinioException;
import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import java.sql.SQLException;

public interface IDeleteService {

    void dateiLoeschen(DateiDTO dateiDTO) throws SQLException, ObjectNotInMinioException;

    void dateiLoeschenStarten(Long dateiId, KeycloakAuthenticationToken token)
            throws SQLException, NoDeletePermissionException, ObjectNotInMinioException;
}
