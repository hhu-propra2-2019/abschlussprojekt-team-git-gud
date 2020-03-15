package de.hhu.propra2.material2.mops.database;

import de.hhu.propra2.material2.mops.database.entities.Datei;
import de.hhu.propra2.material2.mops.database.entities.Gruppe;
import de.hhu.propra2.material2.mops.database.entities.Tag;
import de.hhu.propra2.material2.mops.database.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
public class JpaEntityTest {

    @Autowired
    DateiRepository dateiRepository;

    @Autowired
    GruppeRepository gruppeRepository;

    @Test
    public void createDateiWithTagsTest(){

    }

}
