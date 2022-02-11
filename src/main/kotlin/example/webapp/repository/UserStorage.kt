package spp.example.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import spp.example.webapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage extends CrudRepository<User, Long> {
    List<User> findByFirstname(String firstName);

    List<User> findByLastname(String firstName);

    Optional<User> findByFirstnameAndLastname(String firstName, String lastName);
}