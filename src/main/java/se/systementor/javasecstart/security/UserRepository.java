package se.systementor.javasecstart.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    User getUserByUsername(@Param("username") String username);
}