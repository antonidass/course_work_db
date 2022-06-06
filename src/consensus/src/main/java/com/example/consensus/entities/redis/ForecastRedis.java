package com.example.consensus.entities.redis;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Component
public class ForecastRedis implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String investHouse;
    private Timestamp datePublishing;
    private Timestamp dateEnd;
    private Timestamp dateUpdate;
    private int goalPrice;
    private String forecast;
    private String description;
}
