package de.hhu.propra2.material2.mops;

import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.GruppeRepository;
import de.hhu.propra2.material2.mops.Database.UserRepository;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
public class JDBCTests {

    @Autowired
    private GruppeRepository g;

    @Autowired
    private UserRepository u;

    @Test
    public void testRelationships() {
        GruppeDTO gruppeDTO = new GruppeDTO(1, "Test-Gruppe", "Diese Gruppe ist zum Testen da");
        g.save(gruppeDTO);

        UserDTO userDTO = new UserDTO(5, "Alex", "Amsel", "student");
        userDTO.addGruppe(gruppeDTO);
        u.save(userDTO);
    }
}
