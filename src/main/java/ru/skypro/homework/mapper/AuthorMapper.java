package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Author;

public class AuthorMapper {
    public UserDto toDto(Author author){
        UserDto userDto = new UserDto();
        userDto.setId(author.getId());
        userDto.setEmail(author.getEmail());
        userDto.setFirstName(author.getFirstName());
        userDto.setLastName(author.getLastName());
        userDto.setPhone(author.getPhone());
        userDto.setRole(author.getRole());
        userDto.setImage("TBA");
        return userDto;
    }
}
