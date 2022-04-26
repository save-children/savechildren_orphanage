package com.supung.orphanage.model.dto;

import com.supung.orphanage.model.domain.Orphanage;
import lombok.*;

@Getter
@Setter
@Builder
public class OrphanageInputDTO {
    private String name;
    private String district;
    private String city;

    public Orphanage viewAsOrphanage() {
        return Orphanage.builder().name(name)
                .district(district).city(city)
                .isDeleted(false).build();
    }
}
