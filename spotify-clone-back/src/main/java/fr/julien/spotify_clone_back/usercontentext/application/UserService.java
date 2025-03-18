package fr.julien.spotify_clone_back.usercontentext.application;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import fr.julien.spotify_clone_back.usercontentext.ReadUserDto;
import fr.julien.spotify_clone_back.usercontentext.domain.User;
import fr.julien.spotify_clone_back.usercontentext.mapper.UserMapper;
import fr.julien.spotify_clone_back.usercontentext.repository.UserRepository;

@Service // Indique que cette classe est un service géré par Spring
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Injection des dépendances via le constructeur
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Synchronise les informations de l'utilisateur avec un Identity Provider (IdP).
     * Si l'utilisateur existe déjà dans la base de données, met à jour ses informations si nécessaire.
     * Sinon, l'utilisateur est ajouté à la base.
     */
    public void syncWithIdp(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes(); // Récupère les attributs de l'utilisateur OAuth2
        User user = mapOauth2AttributesToUser(attributes); // Convertit les attributs en entité User
        Optional<User> existingUser = userRepository.findOneByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            if (attributes.get("updated_at") != null) {
                Instant dbLastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
                Instant idpModifiedDate;

                // Vérifie si la date de modification fournie par l'IdP est plus récente
                if (attributes.get("updated_at") instanceof Instant instant) {
                    idpModifiedDate = instant;
                } else {
                    idpModifiedDate = Instant.ofEpochSecond((Integer) attributes.get("updated_at"));
                }
                
                if (idpModifiedDate.isAfter(dbLastModifiedDate)) {
                    updateUser(user); // Met à jour l'utilisateur si les données sont plus récentes
                }
            }
        } else {
            userRepository.saveAndFlush(user); // Sauvegarde un nouvel utilisateur
        }
    }

    /**
     * Récupère l'utilisateur actuellement authentifié depuis le contexte de sécurité.
     * Retourne un objet DTO représentant l'utilisateur.
     */
    public ReadUserDto getAuthenticateUserFromSecurityContext() {
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = mapOauth2AttributesToUser(principal.getAttributes());
        return userMapper.toReadUserDto(user);
    }

    /**
     * Met à jour les informations d'un utilisateur existant.
     */
    private void updateUser(User user) {
        Optional<User> userToUpdateOpt = userRepository.findOneByEmail(user.getEmail());
        if (userToUpdateOpt.isPresent()) {
            User userToUpdate = userToUpdateOpt.get();
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setImageUrl(user.getImageUrl());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setFirstName(user.getFirstName());
            userRepository.saveAndFlush(userToUpdate); // Sauvegarde les modifications
        }
    }

    /**
     * Convertit les attributs OAuth2 en entité utilisateur.
     */
    private User mapOauth2AttributesToUser(Map<String, Object> attributes) {
        User user = new User();
        String sub = String.valueOf(attributes.get("sub")); // Identifiant unique de l'utilisateur OAuth2
        String username = null;

        if (attributes.get("preferred_username") != null) {
            username = ((String) attributes.get("preferred_username")).toLowerCase();
        }
        
        // Récupération des informations personnelles
        if (attributes.get("given_name") != null) {
            user.setFirstName((String) attributes.get("given_name"));
        } else if (attributes.get("name") != null) {
            user.setFirstName((String) attributes.get("name"));
        }
        
        if (attributes.get("family_name") != null) {
            user.setLastName((String) attributes.get("family_name"));
        }
        
        // Détermination de l'email
        if (attributes.get("email") != null) {
            user.setEmail((String) attributes.get("email"));
        } else if (sub.contains("|") && (username != null && username.contains("@"))) {
            user.setEmail(username);
        } else {
            user.setEmail(sub);
        }

        // Récupération de l'URL de l'image de profil
        if (attributes.get("picture") != null) {
            user.setImageUrl((String) attributes.get("picture"));
        }
        
        return user;
    }
}
