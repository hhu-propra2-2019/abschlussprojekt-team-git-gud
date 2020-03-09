package de.hhu.propra2.material2;

import de.hhu.propra2.material2.mops.DTOs.DateiRepository;
import de.hhu.propra2.material2.mops.DTOs.TagRepository;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DataJdbcTest
class Material2ApplicationTests {

    private TagRepository tagRepository;
    private DateiRepository dateiRepository;

    public Material2ApplicationTests(TagRepository t, DateiRepository d) {
        tagRepository = t;
        dateiRepository = d;
    }

    @Test
    public void repoTest() {
        TagDTO tagDTO = new TagDTO();

    }

    @Test
    void contextLoads() {
    }

}
