package fr.julien.spotify_clone_back.usercontentext.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.julien.spotify_clone_back.usercontentext.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
