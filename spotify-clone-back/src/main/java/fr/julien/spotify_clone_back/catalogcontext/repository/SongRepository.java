package fr.julien.spotify_clone_back.catalogcontext.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.julien.spotify_clone_back.catalogcontext.domain.Song;

public interface SongRepository extends JpaRepository<Song, Long> {

}
