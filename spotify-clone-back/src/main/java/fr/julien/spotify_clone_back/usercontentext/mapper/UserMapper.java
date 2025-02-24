package fr.julien.spotify_clone_back.usercontentext.mapper;

import org.mapstruct.Mapper;

import fr.julien.spotify_clone_back.usercontentext.ReadUserDto;
import fr.julien.spotify_clone_back.usercontentext.domain.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ReadUserDto toReadUserDto(User entity);

    public ReadUserDto readUserDTOToUser(User user);
}
