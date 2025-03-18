package fr.julien.spotify_clone_back.usercontentext.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.julien.spotify_clone_back.usercontentext.domain.User;

/**
 * Interface du repository pour la gestion des utilisateurs.
 * Hérite de JpaRepository pour fournir des opérations CRUD sur l'entité User.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son adresse e-mail.
     * 
     * @param email l'adresse e-mail de l'utilisateur recherché
     * @return un Optional contenant l'utilisateur s'il est trouvé, sinon un Optional vide
     */
    Optional<User> findOneByEmail(String email);

}
