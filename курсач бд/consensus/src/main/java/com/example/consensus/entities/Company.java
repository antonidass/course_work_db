package com.example.consensus.entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Table
@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "description")
    private String description;

    @Column(name = "ticker")
    @Size(min = 1, max = 6, message = "Ticker length may be between 1 and 6")
    private String ticker;

    @OneToMany(mappedBy = "companyForForecasts")
    private List<Forecast> forecasts;

    @OneToOne(mappedBy = "company")
    private Indicators indicators;

    public void setFields(Company company) {
        setName(company.getName());
        setLogo(company.getLogo());
        setDescription(company.getDescription());
        setTicker(company.getTicker());
        setForecasts(company.getForecasts());
        setIndicators(company.getIndicators());
    }
}
