package org.effective.taskservice.repositories;

import org.effective.taskservice.domain.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author dayaDanya
 */
@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
}
