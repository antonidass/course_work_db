package com.example.consensus.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Table
@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(generator = "user_id")
    @GenericGenerator(
            name = "user_id",
            strategy = "com.example.consensus.generators.CompanyIdGenerator"
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "description")
    private String description;

    @Column(name = "ticker")
    @NotBlank
    @Size(min = 1, max = 6, message = "Ticker length may be between 1 and 6")
    private String ticker;

    @OneToMany(mappedBy = "companyForForecasts")
    private List<Forecast> forecasts;

    @OneToMany(mappedBy = "companyForNews")
    private List<News> news;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "indicators_id")
    private Indicators indicators;

    public void setFields(Company company) {
        setName(company.getName());
        setLogo(company.getLogo());
        setDescription(company.getDescription());
        setTicker(company.getTicker());
    }
}
