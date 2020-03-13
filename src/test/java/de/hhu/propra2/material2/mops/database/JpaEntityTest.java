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
        Gruppe gruppe = new Gruppe(0, "Test-Gruppe", "Dies ist eine Testgruppe");
        User mark = new User(1, "Axel", "Amsel", "axeam101");
        User axel = new User(2, "Mark", "Meyer", "marme102");

        gruppe.getUsers().add(axel);
        gruppe.getUsers().add(mark);

        mark.getGruppen().add(gruppe);
        axel.getGruppen().add(gruppe);


        LocalDate testDate = LocalDate.now();
        Datei datei = new Datei(0, "Test-File", "/Test", "Heinz Tester", testDate, testDate, 500, "txt", gruppe);
        Tag tag = new Tag(1, "Test");
        Tag tagTwo = new Tag(2, "Wiederholung");

        datei.getTags().add(tag);
        datei.getTags().add(tagTwo);

        tag.getDateien().add(datei);
        tagTwo.getDateien().add(datei);

        gruppeRepository.save(gruppe);
        dateiRepository.save(datei);
    }

}
