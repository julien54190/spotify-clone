package fr.julien.spotify_clone_back.catalogcontext.application.dto;

import fr.julien.spotify_clone_back.catalogcontext.application.vo.SongAuthorVO;
import fr.julien.spotify_clone_back.catalogcontext.application.vo.SongTitleVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SaveSongDTO(@Valid SongTitleVO songTitleVO, 
                        @Valid SongAuthorVO songAuthorVO,
                        @NotNull byte[] cover,
                        @NotNull String coverContentType,
                        @NotNull byte[] file,
                        @NotNull String fileContentType) {

}
