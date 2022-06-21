package com.phunghung29.elasticsearch.controller;

import com.phunghung29.elasticsearch.document.Person;
import com.phunghung29.elasticsearch.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping
    public void save(@RequestBody final Person person) {
        personService.save(person);
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable final String id) {
        return personService.findById(id);
    }
}
