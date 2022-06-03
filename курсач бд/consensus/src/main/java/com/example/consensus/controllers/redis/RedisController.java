package com.example.consensus.controllers.redis;


import com.example.consensus.entities.redis.CompanyRedis;
import com.example.consensus.entities.redis.ForecastRedis;
import com.example.consensus.services.redis.CompanyRedisService;
import com.example.consensus.services.redis.ForecastRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Compression;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/redis")
public class RedisController {

    @Autowired
    CompanyRedisService companyRedisService;

    @Autowired
    ForecastRedisService forecastRedisService;


    @PostMapping("/company")
    public String save(@RequestBody final CompanyRedis employee) {
        companyRedisService.save(employee);
        return "Successfully added. Company with id= " + employee.getId();
    }

    @GetMapping("/company/all")
    public Map<String, CompanyRedis> findAllCompanies() {
        final Map<String, CompanyRedis> employeeMap = (Map<String, CompanyRedis>) companyRedisService.findAll();
        return employeeMap;
    }

    @PostMapping("/forecast")
    public String save(@RequestBody final ForecastRedis employee) {
        forecastRedisService.save(employee);
        return "Successfully added. Forecast with id= " + employee.getId();
    }

    @PostMapping("/forecast/thousand")
    public String saveThousand(@RequestBody final ForecastRedis employee) {
        forecastRedisService.saveThousand(employee);
        return "Successfully added. Forecast with id= " + employee.getId();
    }

    @GetMapping("/forecast/all/time")
    public String findAllTime() {
        Long start = System.currentTimeMillis();
        final Map<String, ForecastRedis> employeeMap = (Map<String, ForecastRedis>) forecastRedisService.findAll();
        Long stop = System.currentTimeMillis();
        return "Время запроса = " + (stop - start);
    }

    @GetMapping("/forecast/all")
    public Map<String, ForecastRedis> findAllForecasts() {
        final Map<String, ForecastRedis> employeeMap = (Map<String, ForecastRedis>) forecastRedisService.findAll();
        return employeeMap;
    }
}
