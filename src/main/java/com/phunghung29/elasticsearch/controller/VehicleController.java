package com.phunghung29.elasticsearch.controller;

import com.phunghung29.elasticsearch.document.Vehicle;
import com.phunghung29.elasticsearch.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService service;

    @Autowired
    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    public void index(@RequestBody final Vehicle vehicle) {
        service.index(vehicle);
    }

    @GetMapping("/{id}")
    public Vehicle getById(@PathVariable final String id) {
        return service.getById(id);
    }
}
