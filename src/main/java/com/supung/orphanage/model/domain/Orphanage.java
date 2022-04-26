package com.supung.orphanage.model.domain;

import com.supung.orphanage.model.dto.OrphanageOutputDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Orphanage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String district;
    private String city;
    private Boolean isDeleted;

    public OrphanageOutputDTO viewAsOrphanageOutputDTO() {
        return OrphanageOutputDTO.builder().id(id)
                .name(name).district(district).city(city)
                .isDeleted(isDeleted).build();
    }
}
