package com.supung.orphanage.repository;

import com.supung.orphanage.model.domain.Orphanage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrphanageRepositoryTest {

    @Autowired
    private OrphanageRepository orphanageRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenTrueOrFalseWithData_whenFindAllByIsDeleted_thenReturnValidResults() {
        //given
        Orphanage deletedOrphanage = Orphanage.builder()
                .name("test_orphanage")
                .district("test").city("test").isDeleted(true)
                .build();
        entityManager.persist(deletedOrphanage);

        //when
        List<Orphanage> orphanageList = orphanageRepository.findAllByIsDeleted(true);

        //then
        assertThat(orphanageList).isNotEmpty();
        assertThat(orphanageList.size()).isEqualTo(1);
        assertThat(orphanageList.get(0).getName()).isEqualTo(deletedOrphanage.getName());
    }

    @Test
    void givenTrueOrFalseAndNodata_whenFindAllByIsDeleted_thenReturnEmptyResults() {
        //when
        List<Orphanage> orphanageList = orphanageRepository.findAllByIsDeleted(true);

        //then
        assertThat(orphanageList).isEmpty();
    }
}
