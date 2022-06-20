package com.phunghung29.elasticsearch.service;

import com.phunghung29.elasticsearch.document.Person;

public interface PersonService {
    void save(final Person person);

    Person findById(final String id);
}
