package fr.julien.spotify_clone_back.catalogcontext.application.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record FavoriteSongDTO(@NotNull boolean favorite, @NotNull UUID publicId) {

}
