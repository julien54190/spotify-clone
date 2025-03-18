package fr.julien.spotify_clone_back.usercontentext.presentation;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.julien.spotify_clone_back.usercontentext.ReadUserDto;
import fr.julien.spotify_clone_back.usercontentext.application.UserService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Contrôleur REST pour gérer l'authentification des utilisateurs.
 */
@RestController
@RequestMapping("/api")
public class AuthResource {

    private final UserService userService;
    private final ClientRegistration registration;

    /**
     * Constructeur de AuthResource.
     * 
     * @param userService    Service pour gérer les utilisateurs.
     * @param registrations  Repository pour récupérer les informations d'authentification.
     */
    public AuthResource(UserService userService, ClientRegistrationRepository registrations) {
        this.userService = userService;
        // Récupère la configuration d'Okta (ou un autre fournisseur OAuth2)
        this.registration = registrations.findByRegistrationId("okta");
    }

    /**
     * Récupère l'utilisateur authentifié et synchronise ses informations avec la base de données.
     * 
     * @param user Utilisateur connecté via OAuth2.
     * @return Les informations de l'utilisateur connecté sous forme de DTO.
     */
    @GetMapping("/get-authenticated-user")
    public ResponseEntity<ReadUserDto> getAuthenticateUser(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            // Si aucun utilisateur n'est authentifié, renvoyer une erreur interne du serveur.
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            // Synchronisation de l'utilisateur avec la base de données.
            userService.syncWithIdp(user);
            // Récupération des informations de l'utilisateur.
            ReadUserDto userFromAuthentication = userService.getAuthenticateUserFromSecurityContext();
            return ResponseEntity.ok().body(userFromAuthentication);
        }
    }

    /**
     * Déconnecte l'utilisateur et génère une URL de déconnexion pour le fournisseur OAuth2.
     * 
     * @param request Requête HTTP pour récupérer les en-têtes.
     * @return Une réponse contenant l'URL de déconnexion.
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Récupération de l'URL du fournisseur OAuth2 (Okta par exemple).
        String issuerUri = registration.getProviderDetails().getIssuerUri();
        // Récupération de l'URL d'origine (frontend).
        String originUrl = request.getHeader(HttpHeaders.ORIGIN);
        
        // Formatage de l'URL de déconnexion.
        Object[] params = {issuerUri, registration.getClientId(), originUrl};
        String logoutUrl = MessageFormat.format("{0}v2/logout?clientid={1}&returnTo={2}", params);
        
        // Invalidation de la session côté serveur.
        request.getSession().invalidate();
        
        // Retourne l'URL de déconnexion sous forme de réponse JSON.
        return ResponseEntity.ok().body(Map.of("logoutUrl", logoutUrl));
    }
}
