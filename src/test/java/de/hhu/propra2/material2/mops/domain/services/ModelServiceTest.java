package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.DateiRepository;
import de.hhu.propra2.material2.mops.Database.GruppeRepository;
import de.hhu.propra2.material2.mops.Database.UserRepository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SuppressWarnings("checkstyle:magicnumber")
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
    @Mock
    private DateiDTO dateiDTOMock1;
    @Mock
    private DateiDTO dateiDTOMock2;
    @Mock
    private DateiDTO dateiDTOMock3;

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

    @Test
    public void dateienDerGruppeZeroFiles() {
        HashMap<GruppeDTO, Boolean> belegungUndRechte = new HashMap<>();
        belegungUndRechte.put(gruppeDTOMock1, true);
        belegungUndRechte.put(gruppeDTOMock2, false);
        angelaDTO = new UserDTO(1,
                "Angela",
                "Merkel",
                "AngelaKCName",
                belegungUndRechte);
        GruppeDTO gruppeDTO = new GruppeDTO(1L, " drei-Datei-Gruppe", new LinkedList<>(), new LinkedList<>());

        List<Datei> result = modelService.dateienDerGruppe(gruppeDTO);

        assertThat(result.size(), is(0));
    }

    @Test
    public void dateienDerGruppeOneFile() {
        HashMap<GruppeDTO, Boolean> belegungUndRechte = new HashMap<>();
        belegungUndRechte.put(gruppeDTOMock1, true);
        belegungUndRechte.put(gruppeDTOMock2, false);
        angelaDTO = new UserDTO(1,
                "Angela",
                "Merkel",
                "AngelaKCName",
                belegungUndRechte);

        when(dateiDTOMock1.getUploader()).thenReturn(angelaDTO);
        when(dateiDTOMock1.getUploaddatum()).thenReturn(new Date());
        when(dateiDTOMock1.getVeroeffentlichungsdatum()).thenReturn(new Date());

        List<DateiDTO> dateien = Collections.singletonList(dateiDTOMock1);
        GruppeDTO gruppeDTO = new GruppeDTO(1L, "eine Datei-Gruppe", new LinkedList<>(), dateien);

        List<Datei> result = modelService.dateienDerGruppe(gruppeDTO);

        assertThat(result.size(), is(1));
    }

    @Test
    public void dateienDerGruppeMultipleFiles() {
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
        when(dateiDTOMock1.getUploaddatum()).thenReturn(new Date());
        when(dateiDTOMock2.getUploaddatum()).thenReturn(new Date());
        when(dateiDTOMock3.getUploaddatum()).thenReturn(new Date());
        when(dateiDTOMock1.getVeroeffentlichungsdatum()).thenReturn(new Date());
        when(dateiDTOMock2.getVeroeffentlichungsdatum()).thenReturn(new Date());
        when(dateiDTOMock3.getVeroeffentlichungsdatum()).thenReturn(new Date());

        List<DateiDTO> dateien = Arrays.asList(dateiDTOMock1, dateiDTOMock2, dateiDTOMock3);
        GruppeDTO gruppeDTO = new GruppeDTO(1L, " drei-Datei-Gruppe", new LinkedList<>(), dateien);

        List<Datei> result = modelService.dateienDerGruppe(gruppeDTO);

        assertThat(result.size(), is(3));
    }

}
