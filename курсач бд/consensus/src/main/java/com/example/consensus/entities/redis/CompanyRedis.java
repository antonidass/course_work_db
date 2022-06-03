package com.example.consensus.entities.redis;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@Component
public class CompanyRedis implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String logo;
    private String description;
    private String ticker;
}
