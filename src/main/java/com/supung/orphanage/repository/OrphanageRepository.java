package com.supung.orphanage.repository;

import com.supung.orphanage.model.domain.Orphanage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrphanageRepository extends JpaRepository<Orphanage, Long> {

    List<Orphanage> findAllByIsDeleted(boolean isDeleted);
}
