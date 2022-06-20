package com.phunghung29.elasticsearch.service.impl;

import com.phunghung29.elasticsearch.document.Person;
import com.phunghung29.elasticsearch.repository.PersonRepository;
import com.phunghung29.elasticsearch.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public void save(final Person person) {
        personRepository.save(person);
    }

    public Person findById(final String id) {
        return personRepository.findById(id).orElse(null);
    }
}
