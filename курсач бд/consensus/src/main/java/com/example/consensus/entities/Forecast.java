package com.example.consensus.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Table
@Entity
@Data
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Column(name = "invest_house")
    private String investHouse;

    @Column(name = "date_publishing")
    private Timestamp datePublishing;

    @Column(name = "date_end")
    private Timestamp dateEnd;

    @Column(name = "date_update")
    private Timestamp dateUpdate;

    @Column(name = "goal_price")
    private int goalPrice;

    @Column(name = "forecast")
    private String forecast;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Company companyForForecasts;


    public void setFields(Forecast forecast) {
        setInvestHouse(forecast.getInvestHouse());
        setDatePublishing(forecast.getDatePublishing());
        setDateEnd(forecast.getDateEnd());
        setDateUpdate(forecast.getDateUpdate());
        setGoalPrice(forecast.getGoalPrice());
        setForecast(forecast.getForecast());
        setDescription(forecast.getDescription());
        setCompanyForForecasts(forecast.getCompanyForForecasts());
    }
}
