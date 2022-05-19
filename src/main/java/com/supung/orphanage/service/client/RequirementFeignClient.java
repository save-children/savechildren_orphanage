package com.supung.orphanage.service.client;

import com.supung.orphanage.model.dto.RequirementOutputDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("REQUIREMENT")
public interface RequirementFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/requirements/getByOrphanageId/{orphanageId}", consumes = "application/json")
    ResponseEntity<List<RequirementOutputDTO>> getRequirementsByOrphanageId(@RequestHeader("correlation-id") String correlationId,
                                                                            @PathVariable("orphanageId") long orphanageId);
}
