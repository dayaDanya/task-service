package org.effective.taskservice.services;

import org.effective.taskservice.domain.models.Person;

public interface PersonService {
    Person findByEmail(String email);
}
