package fr.julien.spotify_clone_back.usercontentext.domain;

import fr.julien.spotify_clone_back.sharedkermel.domain.AbstractAuditingEntity;
import fr.julien.spotify_clone_back.usercontentext.Subscription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Représente un utilisateur dans l'application.
 * Cette classe est une entité JPA qui sera mappée à la table "spotify_user".
 */
@Entity
@Table(name = "spotify_user")
public class User extends AbstractAuditingEntity<Long> {

    // Identifiant unique de l'utilisateur
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    // Nom de famille de l'utilisateur
    @Column(name = "last_name")
    private String lastName;

    // Prénom de l'utilisateur
    @Column(name = "first_name")
    private String firstName;

    // Adresse e-mail de l'utilisateur (doit être unique)
    @Column(name = "email", unique = true)
    private String email;

    // Abonnement de l'utilisateur, par défaut en mode GRATUIT
    private Subscription subscription = Subscription.FREE;

    // URL de l'image de profil de l'utilisateur
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * Getter pour l'identifiant de l'utilisateur.
     * @return id de l'utilisateur
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Setter pour l'identifiant de l'utilisateur.
     * @param id nouvel identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter pour le nom de famille.
     * @return nom de famille de l'utilisateur
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter pour le nom de famille.
     * @param lastName nouveau nom de famille
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter pour le prénom.
     * @return prénom de l'utilisateur
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter pour le prénom.
     * @param firstName nouveau prénom
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter pour l'email.
     * @return email de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter pour l'email.
     * @param email nouvel email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter pour le type d'abonnement.
     * @return abonnement de l'utilisateur
     */
    public Subscription getSubscription() {
        return subscription;
    }

    /**
     * Setter pour le type d'abonnement.
     * @param subscription nouveau type d'abonnement
     */
    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    /**
     * Getter pour l'URL de l'image de profil.
     * @return URL de l'image de l'utilisateur
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Setter pour l'URL de l'image de profil.
     * @param imageUrl nouvelle URL de l'image
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
