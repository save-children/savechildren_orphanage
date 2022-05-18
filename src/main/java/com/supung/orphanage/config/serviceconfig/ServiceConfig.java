package com.supung.orphanage.config.serviceconfig;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "orphanage")
@EnableConfigurationProperties
@Data
public class ServiceConfig {
    private String displayName;
    private Map<String, String> mailDetails;
    private List<String> activeBranches;
}
