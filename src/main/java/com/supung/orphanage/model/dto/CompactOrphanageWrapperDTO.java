package com.supung.orphanage.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CompactOrphanageWrapperDTO {
    private OrphanageOutputDTO orphanage;
    private List<RequirementOutputDTO> requirements;
}
