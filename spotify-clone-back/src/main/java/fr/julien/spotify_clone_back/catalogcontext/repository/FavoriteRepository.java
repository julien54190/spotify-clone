package fr.julien.spotify_clone_back.catalogcontext.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.julien.spotify_clone_back.catalogcontext.domain.Favorite;
import fr.julien.spotify_clone_back.catalogcontext.domain.FavoriteID;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteID> {

}
