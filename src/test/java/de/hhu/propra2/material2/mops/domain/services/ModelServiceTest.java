package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.DateiRepository;
import de.hhu.propra2.material2.mops.Database.GruppeRepository;
import de.hhu.propra2.material2.mops.Database.UserRepository;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {

    @Mock
    private DateiRepository dateiRepoMock;
    @Mock
    private GruppeRepository gruppenRepoMock;
    @Mock
    private UserRepository userRepoMock;
    @Mock
    private GruppeDTO gruppeDTOMock1;
    @Mock
    private GruppeDTO gruppeDTOMock2;

    private UserDTO angelaDTO;

    private ModelService modelService;

    /**
     * setUp for every test.
     * Creates a new ModelService object with mocked repositories.
     */
    @BeforeEach
    @SuppressWarnings("checkstyle:magicnumber")
    public void setUp() {
        this.modelService = new ModelService(dateiRepoMock,
                gruppenRepoMock,
                userRepoMock);
    }

    @Test
    public void getAlleGruppenByUserNameIsNull() {
        when(userRepoMock.findByKeycloakname("")).thenReturn(null);
        List<Gruppe> result = modelService.getAlleGruppenByUser("");
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertGroupHashMapTwoGroups() {
        when(gruppeDTOMock1.getId()).thenReturn(1L);
        when(gruppeDTOMock2.getId()).thenReturn(2L);

        HashMap<GruppeDTO, Boolean> belegungUndRechte = new HashMap<>();
        belegungUndRechte.put(gruppeDTOMock1, true);
        belegungUndRechte.put(gruppeDTOMock2, false);
        angelaDTO = new UserDTO(1,
                "Angela",
                "Merkel",
                "AngelaKCName",
                belegungUndRechte);

        HashMap<Gruppe, Boolean> result = modelService.convertHashMapGruppeDTOtoGruppe(angelaDTO);

        assertThat(result.size(), is(2));
    }
}
