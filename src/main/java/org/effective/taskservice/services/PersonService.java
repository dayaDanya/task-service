package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.repositories.PersonRepo;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepo personRepo;

    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public Person findByEmail(String email) {
        return personRepo.findByEmail(email).orElseThrow(() -> new PersonNotFoundException(email));
    }
}
