package com.supung.orphanage.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class OrphanageOutputDTO {
    private Long id;
    private String name;
    private String district;
    private String city;
    private Boolean isDeleted;
}
