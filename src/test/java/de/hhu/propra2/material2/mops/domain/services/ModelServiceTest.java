package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SuppressWarnings("checkstyle:magicnumber")
@ExtendWith(MockitoExtension.class)
class ModelServiceTest {

    @Mock
    private Repository repoMock;
    @Mock
    private GruppeDTO gruppeDTOMock1;
    @Mock
    private GruppeDTO gruppeDTOMock2;
    @Mock
    private DateiDTO dateiDTOMock1;
    @Mock
    private DateiDTO dateiDTOMock2;
    @Mock
    private DateiDTO dateiDTOMock3;

    private UserDTO angelaDTO;

    private ModelService modelService;
    @Mock
    private SuchService suchServiceMock;
    /**
     * setUp for every test.
     * Creates a new ModelService object with mocked repositories.
     */
    @BeforeEach
    @SuppressWarnings("checkstyle:magicnumber")
    void setUp() {
        this.modelService = new ModelService(
                repoMock,
                suchServiceMock);
    }

    @Test
    void convertGroupHashMapTwoGroups() {
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

    @Test
    void dateienDerGruppeZeroFiles() {
        HashMap<GruppeDTO, Boolean> belegungUndRechte = new HashMap<>();
        belegungUndRechte.put(gruppeDTOMock1, true);
        belegungUndRechte.put(gruppeDTOMock2, false);
        angelaDTO = new UserDTO(1,
                "Angela",
                "Merkel",
                "AngelaKCName",
                belegungUndRechte);
        GruppeDTO gruppeDTO = new GruppeDTO(1L, " drei-Datei-Gruppe", "", new LinkedList<>());

        List<Datei> result = modelService.dateienDerGruppe(gruppeDTO);

        assertThat(result.size(), is(0));
    }

    @Test
    void dateienDerGruppeOneFile() {
        HashMap<GruppeDTO, Boolean> belegungUndRechte = new HashMap<>();
        belegungUndRechte.put(gruppeDTOMock1, true);
        belegungUndRechte.put(gruppeDTOMock2, false);
        angelaDTO = new UserDTO(1,
                "Angela",
                "Merkel",
                "AngelaKCName",
                belegungUndRechte);

        when(dateiDTOMock1.getUploader()).thenReturn(angelaDTO);

        List<DateiDTO> dateien = Collections.singletonList(dateiDTOMock1);
        GruppeDTO gruppeDTO = new GruppeDTO(1L, "eine Datei-Gruppe", "", dateien);

        List<Datei> result = modelService.dateienDerGruppe(gruppeDTO);

        assertThat(result.size(), is(1));
    }

    @Test
    void dateienDerGruppeMultipleFiles() {
        HashMap<GruppeDTO, Boolean> belegungUndRechte = new HashMap<>();
        belegungUndRechte.put(gruppeDTOMock1, true);
        belegungUndRechte.put(gruppeDTOMock2, false);
        angelaDTO = new UserDTO(1,
                "Angela",
                "Merkel",
                "AngelaKCName",
                belegungUndRechte);

        when(dateiDTOMock1.getUploader()).thenReturn(angelaDTO);
        when(dateiDTOMock2.getUploader()).thenReturn(angelaDTO);
        when(dateiDTOMock3.getUploader()).thenReturn(angelaDTO);

        List<DateiDTO> dateien = Arrays.asList(dateiDTOMock1, dateiDTOMock2, dateiDTOMock3);
        GruppeDTO gruppeDTO = new GruppeDTO(1L, " drei-Datei-Gruppe", "", dateien);

        List<Datei> result = modelService.dateienDerGruppe(gruppeDTO);

        assertThat(result.size(), is(3));
    }

}
