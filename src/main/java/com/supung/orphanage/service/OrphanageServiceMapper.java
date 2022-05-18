package com.supung.orphanage.service;

import com.supung.orphanage.model.dto.RequirementOutputDTO;
import com.supung.orphanage.service.client.RequirementFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrphanageServiceMapper {

    private final RequirementFeignClient requirementFeignClient;

    public OrphanageServiceMapper(final RequirementFeignClient requirementFeignClient) {
        this.requirementFeignClient = requirementFeignClient;
    }

    @CircuitBreaker(name = "getWithRequirements", fallbackMethod = "getRequirementsByOrphanageIdFallback")
    public List<RequirementOutputDTO> getRequirementsByOrphanageId(long orphanageId) {
        return requirementFeignClient.getRequirementsByOrphanageId(orphanageId).getBody();
    }

    public List<RequirementOutputDTO> getRequirementsByOrphanageIdFallback(long orphanageId, Throwable t) {
        return null;
    }
}
