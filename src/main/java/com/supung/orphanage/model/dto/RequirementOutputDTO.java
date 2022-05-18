package com.supung.orphanage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RequirementOutputDTO {
    private Long id;
    private Long orphanageId;
    private String title;
    private String description;
    private Long quantity;
}
