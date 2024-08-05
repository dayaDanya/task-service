package org.effective.taskservice.services.implementations;

import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.repositories.PersonRepo;
import org.effective.taskservice.services.PersonService;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepo personRepo;

    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public Person findByEmail(String email) {
        return personRepo.findByEmail(email).orElseThrow(() -> new PersonNotFoundException(email));
    }
}
