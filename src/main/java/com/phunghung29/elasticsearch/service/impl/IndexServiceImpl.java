package com.phunghung29.elasticsearch.service.impl;

import com.phunghung29.elasticsearch.helper.Indices;
import com.phunghung29.elasticsearch.helper.Utils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class IndexServiceImpl {

    private final Logger LOG = LoggerFactory.getLogger(IndexServiceImpl.class);
    private final List<String> INDICES_TO_CREATE = List.of(Indices.VEHICLE_INDEX);
    private final RestHighLevelClient client;

    @Autowired
    public IndexServiceImpl(RestHighLevelClient client) {
        this.client = client;
    }

    @PostConstruct
    public void tryToCreateIndices() {
        final String settings = Utils.loadAsString("static/es-settings.json");
        for (final String indexName : INDICES_TO_CREATE) {
            try {
                boolean indexExists = client.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                if (indexExists) {
                    continue;
                }
                final String mappings = Utils.loadAsString("static/mappings/" + indexName + ".json");
                if (settings == null || mappings == null) {
                    LOG.error("Failed to create index with name {}", indexName);
                    continue;
                }
                final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                createIndexRequest.settings(settings, XContentType.JSON);
                createIndexRequest.mapping(mappings, XContentType.JSON);

                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            } catch (final Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
