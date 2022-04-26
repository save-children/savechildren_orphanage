package com.supung.orphanage.service;

import com.supung.orphanage.model.dto.OrphanageInputDTO;
import com.supung.orphanage.model.dto.OrphanageOutputDTO;

import java.util.List;

public interface OrphanageService {
    OrphanageOutputDTO save(OrphanageInputDTO orphanageInputDTO);

    List<OrphanageOutputDTO> findAll(Boolean isDeleted);

    OrphanageOutputDTO findById(long id);

    OrphanageOutputDTO update(long id, OrphanageInputDTO orphanageInputDTO);

    OrphanageOutputDTO inactivate(long id);
}
