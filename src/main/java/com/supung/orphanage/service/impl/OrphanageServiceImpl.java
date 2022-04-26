package com.supung.orphanage.service.impl;

import com.supung.orphanage.exception.ResourceNotFoundException;
import com.supung.orphanage.model.domain.Orphanage;
import com.supung.orphanage.model.dto.OrphanageInputDTO;
import com.supung.orphanage.model.dto.OrphanageOutputDTO;
import com.supung.orphanage.repository.OrphanageRepository;
import com.supung.orphanage.service.OrphanageService;
import com.supung.orphanage.util.Constants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrphanageServiceImpl implements OrphanageService {

    private OrphanageRepository orphanageRepository;

    public OrphanageServiceImpl(OrphanageRepository orphanageRepository) {
        this.orphanageRepository = orphanageRepository;
    }

    @Override
    public OrphanageOutputDTO save(OrphanageInputDTO orphanageInputDTO) {
        return this.save(orphanageInputDTO.viewAsOrphanage())
                .viewAsOrphanageOutputDTO();
    }

    @Override
    public List<OrphanageOutputDTO> findAll(Boolean isDeleted) {
        List<Orphanage> orphanages = null;
        if(Objects.isNull(isDeleted)) {
            orphanages = orphanageRepository.findAll();
        } else {
            orphanages = orphanageRepository.findAllByIsDeleted(isDeleted);
        }

        return orphanages.stream()
                .map(Orphanage::viewAsOrphanageOutputDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrphanageOutputDTO findById(long id) {
        return this.doFindById(id).viewAsOrphanageOutputDTO();
    }

    @Override
    public OrphanageOutputDTO update(long id, OrphanageInputDTO orphanageInputDTO) {
        this.doFindById(id);
        return this.save(this.getOrphanage(id, orphanageInputDTO))
                .viewAsOrphanageOutputDTO();
    }

    @Override
    public OrphanageOutputDTO inactivate(long id) {
        Orphanage orphanage = this.doFindById(id);
        orphanage.setIsDeleted(true);

        return this.save(orphanage).viewAsOrphanageOutputDTO();
    }

    private Orphanage save(Orphanage orphanage) {
        return orphanageRepository.save(orphanage);
    }

    private Orphanage doFindById(long id) {
        return orphanageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.NO_DATA_FOUND_FOR_GIVEN_ID));
    }

    private Orphanage getOrphanage(long id, OrphanageInputDTO orphanageInputDTO) {
        Orphanage toBeSaved = orphanageInputDTO.viewAsOrphanage();
        toBeSaved.setId(id);
        return toBeSaved;
    }
}
