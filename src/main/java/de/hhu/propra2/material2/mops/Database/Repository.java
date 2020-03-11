package de.hhu.propra2.material2.mops.Database;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Repository {
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/Ziegel24", "root", "geheim");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Repository() { }

    public static UserDTO findUserByKeycloakname(final String keyCloakNameArg) throws SQLException {
        UserDTO user = null;

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from User where key_cloak_name=?");
        preparedStatement.setString(1, keyCloakNameArg);

        ResultSet users = preparedStatement.executeQuery();

        users.next();
        user = new UserDTO(users.getLong("userID"),
                users.getString("vorname"),
                users.getString("nachname"),
                users.getString("key_cloak_name"),
                findAllGruppeByUserID(users.getLong("userID")));

        return user;
    }

    /**
     * Supress magic numbers error because those numbers are parameters
     * @param dateiDTO
     * @throws SQLException
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public static void saveDatei(final DateiDTO dateiDTO) throws SQLException {
        List<TagDTO> tags = dateiDTO.getTagDTOs();
        for (TagDTO tag: tags) {
            saveTagnutzung(dateiDTO, tag);
        }

        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into Datei (name, pfad, uploaderID, upload_datum,"
                                + "veroeffentlichungs_datum, datei_groesse,"
                                + "datei_typ, gruppeID, kategorie) "
                                + " values (?, ?, ?, ?, ?, ?, ? ,?, ?)");

        preparedStatement.setString(1, dateiDTO.getName());
        preparedStatement.setString(2, dateiDTO.getPfad());
        preparedStatement.setLong(3, dateiDTO.getUploader().getId());
        preparedStatement.setDate(4, java.sql.Date.valueOf(dateiDTO.getUploaddatum())); //required sql.date
        preparedStatement.setDate(5, java.sql.Date.valueOf(dateiDTO.getVeroeffentlichungsdatum()));
        preparedStatement.setLong(6, dateiDTO.getDateigroesse());
        preparedStatement.setString(7, dateiDTO.getDateityp());
        preparedStatement.setLong(8, dateiDTO.getGruppe().getId());
        preparedStatement.setString(9, dateiDTO.getKategorie());


        preparedStatement.execute();
    }

<<<<<<< HEAD
    private static void saveTagnutzung(DateiDTO dateiDTO, TagDTO tagDTO) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into Tagnutzung (dateiID, tagID)" + " values (?, ?)");

        preparedStatement.setLong(1, dateiDTO.getId());
        preparedStatement.setLong(2, tagDTO.getId());
        preparedStatement.execute();
    }

    public static void saveTag(final TagDTO tagDTO) throws SQLException {
=======
    private static void saveTag(final TagDTO tagDTO) throws SQLException {
>>>>>>> b4cb3984cd5358e047e3072453f677ed1aed5272
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert ignore into Tags (tag_name)" + " values (?)");

        preparedStatement.setString(1, tagDTO.getText());
        preparedStatement.execute();
    }

    public static void saveUser(final UserDTO userDTO) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into User (userID, vorname, nachname, key_cloak_name)" + " values (?, ?, ?, ?)");

        preparedStatement.setLong(1, userDTO.getId());
        preparedStatement.setString(2, userDTO.getVorname());
        preparedStatement.setString(3, userDTO.getNachname());
        preparedStatement.setString(4, userDTO.getKeycloakname());
        preparedStatement.execute();
    }

    public static void saveGruppe(final GruppeDTO gruppeDTO) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "insert into Gruppe (gruppeID, titel, beschreibung)" + " values (?, ?, ?)");

        preparedStatement.setLong(1, gruppeDTO.getId());
        preparedStatement.setString(2, gruppeDTO.getName());
        preparedStatement.setString(3, gruppeDTO.getDescription());
        preparedStatement.execute();
    }


    private static HashMap<GruppeDTO, Boolean> findAllGruppeByUserID(final long userId) throws SQLException {
        HashMap<GruppeDTO, Boolean> gruppen = new HashMap<GruppeDTO, Boolean>();

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Gruppenbelegung where userID=?");
        preparedStatement.setString(1, "" + userId);
        ResultSet gruppenResult = preparedStatement.executeQuery();

        while (gruppenResult.next()) {
            gruppen.put(findGruppeById(gruppenResult.getLong("gruppenID")),
                    gruppenResult.getBoolean("upload_berechtigung"));
        }

        return gruppen;
    }

    private static GruppeDTO findGruppeById(final long id) throws SQLException {
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

    private static ArrayList<DateiDTO> findAllDateiByGruppeId(final long gruppeId) throws SQLException {
        ArrayList<DateiDTO> dateien = new ArrayList<DateiDTO>();

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Datei where gruppeID=?");
        preparedStatement.setString(1, "" + gruppeId);

        ResultSet dateiResult = preparedStatement.executeQuery();
        while (dateiResult.next()) {
            dateien.add(findDateiById(dateiResult.getLong("dateiID")));
        }

        return dateien;
    }

    private static DateiDTO findDateiById(final long id) throws SQLException {
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
                    dateiResult.getDate("upload_datum").toLocalDate(),
                    dateiResult.getDate("veroeffentlichungs_datum").toLocalDate(),
                    dateiResult.getLong("datei_groesse"),
                    dateiResult.getString("datei_typ"),
                    null,
                    dateiResult.getString("kategorie"));


        return datei;
    }

    private static ArrayList<TagDTO> findAllTagsbyDateiId(final long dateiId) throws SQLException {
        ArrayList<TagDTO> tags = new ArrayList<TagDTO>();

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Tagnutzung where dateiID=?");
        preparedStatement.setString(1, "" + dateiId);

        ResultSet tagResult = preparedStatement.executeQuery();

        while (tagResult.next()) {
            tags.add(findTagById(tagResult.getLong("tagID")));
        }

        return tags;
    }

    private static TagDTO findTagById(final long id) throws SQLException {
        TagDTO tag = null;

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Tags where tagID=?");
        preparedStatement.setString(1, "" + id);

        ResultSet tagResult = preparedStatement.executeQuery();

        tagResult.next();

        tag = new TagDTO(tagResult.getLong("tagID"), tagResult.getString("tag_name"));

        return tag;
    }

    private static ArrayList<UserDTO> findAllUserByGruppeId(final long gruppeId) throws SQLException {
        ArrayList<UserDTO> users = new ArrayList<UserDTO>();

        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from Gruppenbelegung where gruppeID=?");
        preparedStatement.setString(1, "" + gruppeId);

        ResultSet userResult = preparedStatement.executeQuery();
        while (userResult.next()) {
            users.add(findUserByIdLAZY(userResult.getLong("userID")));
        }
        return users;
    }

    private static UserDTO findUserByIdLAZY(final long id) throws SQLException {
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
