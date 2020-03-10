package de.hhu.propra2.material2.mops.Database;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
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
                new HashMap<GruppeDTO, Boolean>());

        return user;
    }

    private static HashMap<GruppeDTO, Boolean> findAllGruppeByUserID (long userId) throws SQLException {
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
        gruppe = new GruppeDTO(id,
                gruppeResult.getString("titel"),
                gruppeResult.getString("beschreibung"),
                findAllUserByGruppeId(id),
                findAllDateiByGruppeId(id));
        for (DateiDTO datei: gruppe.getDateien()) {
            datei.setGruppe(gruppe);
        }

        return gruppe;
    }

    private static ArrayList<DateiDTO> findAllDateiByGruppeId(long gruppeId) throws SQLException {
        ArrayList<DateiDTO> dateien = new ArrayList<DateiDTO>();

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Datei where gruppeID=?");
        preparedStatement.setString(1, "" + gruppeId);

        ResultSet dateiResult = preparedStatement.executeQuery();
        while(dateiResult.next()) {
            dateien.add(findDateiById(dateiResult.getLong("dateiID")));
        }

        return dateien;
    }

    private static DateiDTO findDateiById(long id) throws SQLException {
        DateiDTO datei = null;

            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from Datei where dateiID=?");
            preparedStatement.setString(1, "" + id);

            ResultSet dateiResult = preparedStatement.executeQuery();
            dateiResult.next();

            datei = new DateiDTO(dateiResult.getLong("dateiID"),
                    dateiResult.getString("name"),
                    dateiResult.getString("pfad"),
                    findUserByIdLAZY(dateiResult.getLong("uploaderID")),
                    findAllTagsbyDateiId(id),
                    dateiResult.getDate("upload_datum"),
                    dateiResult.getDate("veroeffentlichungs_datum"),
                    dateiResult.getLong("datei_groesse"),
                    dateiResult.getString("datei_typ"),
                    null,
                    dateiResult.getString("kategorie"));


        return datei;
    }

    private static ArrayList<TagDTO> findAllTagsbyDateiId(long dateiId) throws SQLException {
        ArrayList<TagDTO> tags = new ArrayList<TagDTO>();

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Tagnutzung where dateiID=?");
        preparedStatement.setString(1, "" + dateiId);

        ResultSet tagResult = preparedStatement.executeQuery();

        while(tagResult.next()) {
            tags.add(findTagById(tagResult.getLong("tagID")));
        }

        return tags;
    }

    private static TagDTO findTagById(long id) throws SQLException {
        TagDTO tag = null;

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Tags where tagID=?");
        preparedStatement.setString(1, "" + id);

        ResultSet tagResult = preparedStatement.executeQuery();

        tagResult.next();

        tag = new TagDTO(tagResult.getLong("tagID"), tagResult.getString("tag_name"));

        return tag;
    }

    private static ArrayList<UserDTO> findAllUserByGruppeId(long gruppeId) throws SQLException {
        ArrayList<UserDTO> users = new ArrayList<UserDTO>();

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Gruppenbelegung where gruppeID=?");
        preparedStatement.setString(1, "" + gruppeId);

        ResultSet userResult = preparedStatement.executeQuery();
        while(userResult.next()) {
            users.add(findUserByIdLAZY(userResult.getLong("userID")));
        }
        return users;
    }

    private static UserDTO findUserByIdLAZY(long id) throws SQLException {
        UserDTO user = null;

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from User where userID=?");
        preparedStatement.setString(1, "" + id);

        ResultSet userResult = preparedStatement.executeQuery();
        userResult.next();
        user = new UserDTO(id, userResult.getString("vorname"),
                userResult.getString("nachname"),
                userResult.getString("key_cloak_name"),
                null);

        return user;
    }

}
