package fr.julien.spotify_clone_back.usercontentext.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.julien.spotify_clone_back.usercontentext.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String email);

    Optional<User> findOneByEmail(String email);
}
