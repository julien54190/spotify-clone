package fr.julien.spotify_clone_back.catalogcontext.domain;

import java.io.Serializable;
import java.util.UUID;

import fr.julien.spotify_clone_back.catalogcontext.domain.FavoriteID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorite")
@IdClass(FavoriteID.class)
public class Favorite implements Serializable {

    public UUID getSongPublicId() {
        return songPublicId;
    }

    public void setSongPublicId(UUID songPublicId) {
        this.songPublicId = songPublicId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Id
    private UUID songPublicId;

    @Id
    @Column(name = "user_email")
    private String userEmail;

}
