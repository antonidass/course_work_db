package com.example.consensus.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table
@Entity
@Data
public class Indicators {
    @Id
    @GeneratedValue(generator = "user_id")
    @GenericGenerator(
            name = "user_id",
            strategy = "com.example.consensus.generators.IndicatorsIdGenerator"
    )
    private Long id;

    @Column(name = "price")
    @Min(value = 0, message = "Price can not be less then 0!")
    private Double price;

    @Column(name = "market_cap")
    @Min(value = 0, message = "Market cap can not be less then 0!")
    private Double market_cap;

    @Max(value = 100, message = "Income cannot be more than 100!")
    @Column(name = "income")
    private Integer income;

    @Min(value = 0, message = "Revenue must be more then 0!")
    @Column(name = "revenue")
    private Long revenue;

    @OneToOne(mappedBy = "indicators", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Company company;

    public void setFields(Indicators newIndicators) {
        setPrice(newIndicators.getPrice());
        setMarket_cap(newIndicators.getMarket_cap());
        setIncome(newIndicators.getIncome());
        setRevenue(newIndicators.getRevenue());
    }
}
