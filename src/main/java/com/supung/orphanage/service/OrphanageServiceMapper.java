package com.supung.orphanage.service;

import com.supung.orphanage.model.dto.RequirementOutputDTO;
import com.supung.orphanage.service.client.RequirementFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrphanageServiceMapper {

    private final RequirementFeignClient requirementFeignClient;

    public OrphanageServiceMapper(final RequirementFeignClient requirementFeignClient) {
        this.requirementFeignClient = requirementFeignClient;
    }

    @CircuitBreaker(name = "getWithRequirements", fallbackMethod = "getRequirementsByOrphanageIdFallback")
    public List<RequirementOutputDTO> getRequirementsByOrphanageId(String correlationId, long orphanageId) {
        return requirementFeignClient.getRequirementsByOrphanageId(correlationId, orphanageId).getBody();
    }

    public List<RequirementOutputDTO> getRequirementsByOrphanageIdFallback(String correlationId, long orphanageId, Throwable t) {
        log.info("Finding orphanage with requirements by orphanage id : {}, correlationId : {} Found issue connecting requirement MC"
                , orphanageId, correlationId);
        return null;
    }
}
