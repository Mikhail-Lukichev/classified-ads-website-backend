package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Author;

@Component
public class AuthorMapper {
    public UserDto toUserDto(Author author) {
        UserDto userDto = new UserDto();
        userDto.setId(author.getId());
        userDto.setEmail(author.getEmail());
        userDto.setFirstName(author.getFirstName());
        userDto.setLastName(author.getLastName());
        userDto.setPhone(author.getPhone());
        userDto.setRole(author.getRole());
        userDto.setImage("/image/avatar/" + author.getId());
        return userDto;
    }

    public UpdateUserDto toUpdateUserDto(Author author) {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName(author.getFirstName());
        updateUserDto.setLastName(author.getLastName());
        updateUserDto.setPhone(author.getPhone());
        return updateUserDto;
    }

    public Author toAuthor(UpdateUserDto updateUserDto) {
        Author author = new Author();
        author.setFirstName(updateUserDto.getFirstName());
        author.setLastName(updateUserDto.getLastName());
        author.setPhone(updateUserDto.getPhone());
        return author;
    }
}
