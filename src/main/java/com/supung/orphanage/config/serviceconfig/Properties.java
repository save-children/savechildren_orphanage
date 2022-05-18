package com.supung.orphanage.config.serviceconfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Properties {
    private String displayName;
    private Map<String, String> mailDetails;
    private List<String> activeBranches;
}
