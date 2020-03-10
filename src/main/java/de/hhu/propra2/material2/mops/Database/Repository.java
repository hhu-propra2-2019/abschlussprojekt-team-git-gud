package de.hhu.propra2.material2.mops.Database;

import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class Repository {
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/Ziegel24", "root", "geheim");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static UserDTO findUserByKeycloakname(final String keyCloakNameArg) throws SQLException {
        UserDTO user = null;

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from User where key_cloack_name=?");
        preparedStatement.setString(1, keyCloakNameArg);

        ResultSet users = preparedStatement.executeQuery();

        users.next();
        user = new UserDTO(users.getLong("userID"),
                users.getString("vorname"),
                users.getString("nachname"),
                users.getString("key_cloak_name"),
                new HashMap< GruppeDTO, Boolean>());

        return user;
    }

    private static HashMap<GruppeDTO,Boolean> findAllGruppeByUserID (long userId) throws SQLException {
        HashMap<GruppeDTO,Boolean> gruppen = new HashMap<GruppeDTO, Boolean>();

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Gruppenbelegung where userID=?");
        preparedStatement.setString(1, "" + userId);
        ResultSet gruppenResult = preparedStatement.executeQuery();

        while(gruppenResult.next()) {
            gruppen.put(findGruppeById(gruppenResult.getLong("gruppenID")),
                    gruppenResult.getBoolean("upload_berechtigung"));
        }

        return gruppen;
    }

    private static GruppeDTO findGruppeById (long id) throws SQLException {
        GruppeDTO gruppe = null;

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Gruppe where gruppeID=?");
        preparedStatement.setString(1, "" + id);

        ResultSet gruppeResult = preparedStatement.executeQuery();
        gruppeResult.next();


        return gruppe;
    }

    private static ArrayList<UserDTO> findAllUserByGroupId(long id) throws SQLException {
        ArrayList<UserDTO> users = new ArrayList<UserDTO>();

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Gruppenbelegung where gruppeID=?");
        preparedStatement.setString(1, "" + id);

        ResultSet userResult = preparedStatement.executeQuery();
        userResult.next();

    }

}
