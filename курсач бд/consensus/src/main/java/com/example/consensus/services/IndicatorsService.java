package com.example.consensus.services;


import com.example.consensus.exception.FileStorageException;
import com.example.consensus.repositories.IndicatorsRepository;
import com.example.consensus.entities.Indicators;
import org.springframework.stereotype.Service;

@Service
public class IndicatorsService {

    private final IndicatorsRepository indicatorsRepository;

    public IndicatorsService(IndicatorsRepository indicatorsRepository) {
        this.indicatorsRepository = indicatorsRepository;
    }

    public Indicators getIndicatorsByCompanyId(Long companyId) {
        Indicators indicators = indicatorsRepository.findByCompanyId(companyId);

        if (indicators == null) {
            throw new FileStorageException("Indicators with company_id = " + companyId + " not exists!");
        }
        return indicators;
    }

    public Indicators updateIndicatorsByCompanyId(Long companyId, Indicators newIndicators) {
        Indicators indicators = indicatorsRepository.findByCompanyId(companyId);

        if (indicators == null) {
            throw new FileStorageException("Indicators with company_id = " + companyId + " not exists!");
        }
        indicators.setFields(newIndicators);
        return indicatorsRepository.save(indicators);
    }

    public Indicators deleteIndicatorsByCompanyId(Long companyId) {
        Indicators indicators = indicatorsRepository.findByCompanyId(companyId);
        if (indicators == null) {
            throw new FileStorageException("Indicators with company_id = " + companyId + " not exists!");
        }
        indicatorsRepository.deleteById(indicators.getId());
        return indicators;
    }
}
