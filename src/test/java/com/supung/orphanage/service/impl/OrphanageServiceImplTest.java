package com.supung.orphanage.service.impl;

import com.supung.orphanage.exception.ResourceNotFoundException;
import com.supung.orphanage.model.domain.Orphanage;
import com.supung.orphanage.model.dto.OrphanageInputDTO;
import com.supung.orphanage.model.dto.OrphanageOutputDTO;
import com.supung.orphanage.repository.OrphanageRepository;
import com.supung.orphanage.service.OrphanageService;
import com.supung.orphanage.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrphanageServiceImplTest {

    @Mock
    private OrphanageRepository orphanageRepository;

    private OrphanageService orphanageService;

    @BeforeEach
    void setUp() {
        orphanageService = new OrphanageServiceImpl(orphanageRepository);
    }

    @Test
    void givenValidOrphanageInputDTO_whenSave_thenReturnValidOutputDTO() {
        //given
        OrphanageInputDTO orphanageInputDTO = OrphanageInputDTO.builder()
                .name("test_Orphanage").district("test").city("test").build();

        Orphanage orphanage = orphanageInputDTO.viewAsOrphanage();
        orphanage.setId(1l);
        orphanage.setIsDeleted(false);

        when(orphanageRepository.save(any())).thenReturn(orphanage);

        //when
        OrphanageOutputDTO orphanageOutputDTO = orphanageService.save(orphanageInputDTO);

        //then
        assertThat(orphanageOutputDTO.getId()).isEqualTo(1l);
        assertThat(orphanageOutputDTO.getName()).isEqualTo(orphanageInputDTO.getName());
        assertThat(orphanageOutputDTO.getIsDeleted()).isEqualTo(false);
    }

    @Test
    void givenDataAvailable_whenFindAllWithNull_thenReturnOutputDTOs() {
        //given
        List<Orphanage> orphanages = Arrays.asList(Orphanage.builder().id(1l).name("test1")
                .city("test1").district("test1").isDeleted(false).build(),
                Orphanage.builder().id(1l).name("test2")
                        .city("test2").district("test2").isDeleted(true).build());
        when(orphanageRepository.findAll()).thenReturn(orphanages);

        //when
        List<OrphanageOutputDTO> orphanageOutputDTOS = orphanageService.findAll(null);

        //then
        assertThat(orphanageOutputDTOS).isNotEmpty();
        assertThat(orphanageOutputDTOS.size()).isEqualTo(2);
    }

    @Test
    void givenDataAvailable_whenFindAllWithValue_thenReturnOutputDTOs() {
        //given
        Orphanage orphanage = Orphanage.builder().id(1l).name("test2")
                        .city("test2").district("test2").isDeleted(true).build();
        when(orphanageRepository.findAllByIsDeleted(true)).thenReturn(Arrays.asList(orphanage));

        //when
        List<OrphanageOutputDTO> orphanageOutputDTOS = orphanageService.findAll(Boolean.TRUE);

        //then
        assertThat(orphanageOutputDTOS).isNotEmpty();
        assertThat(orphanageOutputDTOS.size()).isEqualTo(1);
        assertThat(orphanageOutputDTOS.get(0).getName()).isEqualTo(orphanage.getName());
    }

    @Test
    void givenDataAvailableAndValidId_whenFindById_thenReturnCorrectOutputDTO() {
        //given
        Orphanage orphanage = Orphanage.builder().id(1l).name("test1")
                        .city("test1").district("test1").isDeleted(false).build();
        when(orphanageRepository.findById(1l)).thenReturn(Optional.of(orphanage));

        //when
        OrphanageOutputDTO orphanageOutputDTO = orphanageService.findById(1l);

        //then
        assertThat(orphanageOutputDTO.getName()).isEqualTo(orphanage.getName());
        assertThat(orphanageOutputDTO.getIsDeleted()).isEqualTo(false);
        assertThat(orphanageOutputDTO.getId()).isEqualTo(1l);
    }

    @Test
    void givenNoDataAvailableAndValidId_whenFindById_thenThrowAnError() {
        //given
        when(orphanageRepository.findById(1l)).thenReturn(Optional.empty());

        //when and then
        assertThatThrownBy(() -> orphanageService.findById(1l))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(Constants.NO_DATA_FOUND_FOR_GIVEN_ID);
    }

    @Test
    void givenValidIDAndValidInput_whenUpdate_thenReturnUpdatedDTO() {
        //given
        OrphanageInputDTO orphanageInputDTO = OrphanageInputDTO.builder()
                .name("test_Orphanage_updated").district("test").city("test").build();
        Orphanage orphanage = orphanageInputDTO.viewAsOrphanage();
        orphanage.setId(1l);
        orphanage.setName("test_Orphanage");
        orphanage.setIsDeleted(false);

        when(orphanageRepository.findById(1l)).thenReturn(Optional.of(orphanage));
        orphanage.setName("test_Orphanage_updated");
        when(orphanageRepository.save(any())).thenReturn(orphanage);

        //when
        OrphanageOutputDTO orphanageOutputDTO = orphanageService.update(1l, orphanageInputDTO);

        //then
        assertThat(orphanageOutputDTO).isNotNull();
        assertThat(orphanageOutputDTO.getName()).isEqualTo(orphanageInputDTO.getName());
    }

    @Test
    void givenInvalidIDAndValidInput_whenUpdate_thenThrowError() {
        //given
        OrphanageInputDTO orphanageInputDTO = OrphanageInputDTO.builder()
                .name("test_Orphanage_updated").district("test").city("test").build();

        //when and then
        assertThatThrownBy(() -> orphanageService.update(1l, orphanageInputDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(Constants.NO_DATA_FOUND_FOR_GIVEN_ID);
        verify(orphanageRepository, never()).save(any());
    }

    @Test
    void givenValidId_whenInactivate_thenReturnInactivatedDTO() {
        //given
        Orphanage orphanage = Orphanage.builder()
                .id(1l).isDeleted(false).name("test_Orphanage_updated")
                .district("test").city("test").build();
        when(orphanageRepository.findById(1l)).thenReturn(Optional.of(orphanage));
        orphanage.setIsDeleted(true);
        when(orphanageRepository.save(any())).thenReturn(orphanage);

        //when
        OrphanageOutputDTO orphanageOutputDTO = orphanageService.inactivate(1l);

        //then
        assertThat(orphanageOutputDTO).isNotNull();
        assertThat(orphanageOutputDTO.getName()).isEqualTo(orphanage.getName());
        assertThat(orphanageOutputDTO.getIsDeleted()).isEqualTo(true);
    }

    @Test
    void givenInvalidId_whenInactivate_thenThrowError() {
        //when and then
        assertThatThrownBy(() -> orphanageService.inactivate(1l))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(Constants.NO_DATA_FOUND_FOR_GIVEN_ID);
        verify(orphanageRepository, never()).save(any());
    }
}
