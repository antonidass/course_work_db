package com.example.consensus.controllers;

import com.example.consensus.entities.Indicators;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.services.IndicatorsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class IndicatorsController {
    private final IndicatorsService indicatorsService;

    public IndicatorsController(IndicatorsService indicatorsService) {
        this.indicatorsService = indicatorsService;
    }

    @GetMapping("/company/{id}/indicators")
    public ResponseEntity<?> getIndicatorsByCompanyId(@PathVariable(name = "id") Long companyId) {
        Indicators indicators;
        try {
            indicators = indicatorsService.getIndicatorsByCompanyId(companyId);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Финансовые показатели у компании с id = " + companyId + " не найдены!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(indicators, HttpStatus.OK);
    }

    @PutMapping("/company/{id}/indicators")
    public ResponseEntity<?> updateIndicatorsByCompanyId(@PathVariable(name = "id") Long companyId, @RequestBody Indicators indicatorsDetails) {
        Indicators indicators;
        try {
            indicators = indicatorsService.updateIndicatorsByCompanyId(companyId, indicatorsDetails);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Финансовые показатели у компании с id = " + companyId + " не найдены!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(indicators, HttpStatus.OK);
    }

    @DeleteMapping("/company/{id}/indicators")
    public ResponseEntity<?> deleteIndicatorsByCompanyId(@PathVariable(name = "id") Long companyId) {
        Indicators indicators;
        try {
            indicators = indicatorsService.deleteIndicatorsByCompanyId(companyId);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Финансовые показатели у компании с id = " + companyId + " не найдены!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(indicators, HttpStatus.OK);
    }
}
