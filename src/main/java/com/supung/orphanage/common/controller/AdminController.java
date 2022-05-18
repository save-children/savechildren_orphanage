package com.supung.orphanage.common.controller;

import com.supung.orphanage.config.serviceconfig.Properties;
import com.supung.orphanage.config.serviceconfig.ServiceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private ServiceConfig serviceConfig;

    @Value("${orphanage.display.name}")
    private String displayName;

    public AdminController(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @GetMapping("/properties")
    public Properties getAllProperties() {
        return Properties.builder()
                .displayName(displayName)
                .activeBranches(serviceConfig.getActiveBranches())
                .mailDetails(serviceConfig.getMailDetails())
                .build();
    }
}
