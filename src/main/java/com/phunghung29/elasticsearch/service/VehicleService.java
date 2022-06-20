package com.phunghung29.elasticsearch.service;

import com.phunghung29.elasticsearch.document.Vehicle;

public interface VehicleService {
    boolean index(final Vehicle vehicle);
    Vehicle getById(final String vehicleId);
}
