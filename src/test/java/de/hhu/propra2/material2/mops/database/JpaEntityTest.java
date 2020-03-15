package de.hhu.propra2.material2.mops.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
