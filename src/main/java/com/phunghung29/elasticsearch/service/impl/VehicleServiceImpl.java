package com.phunghung29.elasticsearch.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phunghung29.elasticsearch.document.Vehicle;
import com.phunghung29.elasticsearch.helper.Indices;
import com.phunghung29.elasticsearch.service.VehicleService;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class VehicleServiceImpl implements VehicleService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(VehicleServiceImpl.class);
    private final RestHighLevelClient client;

    @Autowired
    public VehicleServiceImpl(RestHighLevelClient client) {
        this.client = client;
    }

    @Override
    public boolean index(Vehicle vehicle) {
        try {
            final String vehicleAsString = MAPPER.writeValueAsString(vehicle);

            final IndexRequest request = new IndexRequest(Indices.VEHICLE_INDEX);
            request.id(vehicle.getId());
            request.source(vehicleAsString, XContentType.JSON);

            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            return response != null && response.status().equals(RestStatus.OK);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public Vehicle getById(final String vehicleId) {
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Indices.VEHICLE_INDEX, vehicleId),
                    RequestOptions.DEFAULT
            );
            if (documentFields == null || documentFields.isSourceEmpty()) {
                return null;
            }

            return MAPPER.readValue(documentFields.getSourceAsString(), Vehicle.class);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
