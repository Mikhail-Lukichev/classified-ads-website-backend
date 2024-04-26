package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Author;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {

    private final AuthorMapper authorMapper = new AuthorMapper();

    @Test
    void toUserDtoTest() {
        Author author = TestUtils.getAuthor1();
        UserDto expected = TestUtils.getUserDto1();
        UserDto actual = authorMapper.toUserDto(author);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toUpdateUserDtoTest() {
        Author author = TestUtils.getAuthor1();
        UpdateUserDto expected = TestUtils.getUpdateUserDto1();
        UpdateUserDto actual = authorMapper.toUpdateUserDto(author);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateUserDtotoAuthorTest() {
        UpdateUserDto dto = TestUtils.getUpdateUserDto1();
        Author expected = new Author();
        expected.setFirstName(dto.getFirstName());
        expected.setLastName(dto.getLastName());
        expected.setPhone(dto.getPhone());
        Author actual = authorMapper.toAuthor(dto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void registerDtotoAuthorTest() {
        RegisterDto dto = TestUtils.getRegisterDto1();
        Author expected = new Author();
        expected.setFirstName(dto.getFirstName());
        expected.setLastName(dto.getLastName());
        expected.setPhone(dto.getPhone());
        expected.setEmail(dto.getUsername());
        expected.setPassword(dto.getPassword());
        expected.setRole(dto.getRole());
        Author actual = authorMapper.toAuthor(dto);
        Assertions.assertEquals(expected, actual);
    }
}