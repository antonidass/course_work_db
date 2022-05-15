package com.example.consensus.controllers;

import com.example.consensus.entities.Indicators;
import com.example.consensus.services.IndicatorsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class IndicatorsController {
    private final IndicatorsService indicatorsService;

    public IndicatorsController(IndicatorsService indicatorsService) {
        this.indicatorsService = indicatorsService;
    }

    @GetMapping("/company/{id}/indicators")
    public Indicators getIndicatorsByCompanyId(@PathVariable(name = "id") Long companyId) {
        return indicatorsService.getIndicatorsByCompanyId(companyId);
    }

    @PutMapping("/company{id}/indicators")
    public Indicators updateIndicatorsByCompanyId(@PathVariable(name = "id") Long companyId, @RequestBody Indicators indicators) {
        return indicatorsService.updateIndicatorsByCompanyId(companyId, indicators);
    }

    @DeleteMapping("/company{id}/indicators")
    public Indicators deleteIndicatorsByCompanyId(@PathVariable(name = "id") Long companyId) {
        return indicatorsService.deleteIndicatorsByCompanyId(companyId);
    }
}
