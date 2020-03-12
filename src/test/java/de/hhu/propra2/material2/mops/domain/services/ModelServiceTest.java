package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DateiRepository;
import de.hhu.propra2.material2.mops.Database.GruppeRepository;
import de.hhu.propra2.material2.mops.Database.UserRepository;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ModelService.class)
public class ModelServiceTest {


    @Mock
    private DateiRepository dateiRepoMock;
    @Mock
    private GruppeRepository gruppenRepoMock;
    @Mock
    private UserRepository userRepoMock;
    @Mock
    private ModelService modelServiceMock;
    @Mock
    private User userMock;
    @Mock
    private Gruppe gruppeMock1;
    @Mock
    private Gruppe gruppeMock2;
    @Mock
    private User uploaderMock;
    private ModelService modelService;

    /**
     * setUp for every test.
     * Creates a new ModelService object with mocked repositories.
     */
    @BeforeEach
    public void setUp() {
        this.modelService = new ModelService(dateiRepoMock,
                gruppenRepoMock,
                userRepoMock);
    }

    @Test
    public void test() {
        assertEquals(1, 1);
    }
}
