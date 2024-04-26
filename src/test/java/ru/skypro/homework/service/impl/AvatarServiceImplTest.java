package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.TestUtils;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.repository.AvatarRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvatarServiceImplTest {

    @Mock
    private AvatarRepository avatarRepository;

    @InjectMocks
    private AvatarServiceImpl out;

    @Test
    void getByIdTest() {
        Optional<Avatar> expected = Optional.of(TestUtils.getAvatar());
        when(avatarRepository.findById(anyInt())).thenReturn(expected);
        Optional<Avatar> actual = out.getById(1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByAuthor() {
        Optional<Avatar> expected = Optional.of(TestUtils.getAvatar());
        Author author = TestUtils.getAuthor1();
        when(avatarRepository.findByAuthor(any())).thenReturn(expected);
        Optional<Avatar> actual = out.getByAuthor(author);
        Assertions.assertEquals(expected, actual);
    }
}