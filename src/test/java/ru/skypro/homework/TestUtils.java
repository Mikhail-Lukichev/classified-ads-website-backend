package ru.skypro.homework;

import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Author;
import ru.skypro.homework.entity.Comment;

import java.util.List;

public class TestUtils {

    private static String adImagesPath = "/image/adImage/";

    private static String avatarPath = "/image/avatar/";

    public static Ad getAd1() {
        Ad ad = new Ad();
        ad.setId(1);
        ad.setPrice(111);
        ad.setTitle("title1");
        ad.setDescription("description1");
        Author author = new Author();
        author.setId(1);
        author.setFirstName("firstname1");
        author.setLastName("lastname1");
        author.setPhone("111");
        author.setEmail("email1");

        ad.setAuthor(author);
        return ad;
    }

    public static Ad getAd2() {
        Ad ad = new Ad();
        ad.setId(2);
        ad.setPrice(222);
        ad.setTitle("title2");
        ad.setDescription("description2");
        Author author = new Author();
        author.setId(2);
        ad.setAuthor(author);
        return ad;
    }

    public static AdDto getAdDto1(){
        AdDto adDto = new AdDto();
        adDto.setAuthor(1);
        adDto.setImage(adImagesPath + "1");
        adDto.setPk(1);
        adDto.setPrice(111);
        adDto.setTitle("title1");
        return adDto;
    }

    public static AdDto getAdDto2(){
        AdDto adDto = new AdDto();
        adDto.setAuthor(2);
        adDto.setImage(adImagesPath + "2");
        adDto.setPk(2);
        adDto.setPrice(222);
        adDto.setTitle("title2");
        return adDto;
    }

    public static AdsDto getAdsDto() {
        AdsDto adsDto = new AdsDto();
        AdDto adDto1 = getAdDto1();
        AdDto adDto2 = getAdDto2();
        adsDto.setCount(2);
        adsDto.setResults(List.of(adDto1,adDto2));
        return adsDto;
    }

    public  static CreateOrUpdateAdDto getCreateOrUpdateAdDto1() {
        CreateOrUpdateAdDto dto = new CreateOrUpdateAdDto();
        dto.setTitle("title1");
        dto.setPrice(111);
        dto.setDescription("description1");
        return dto;
    }

    public static ExtendedAdDto getExtendedAdDto1() {
        ExtendedAdDto dto = new ExtendedAdDto();
        dto.setPk(1);
        dto.setAuthorFirstName("firstname1");
        dto.setAuthorLastName("lastname1");
        dto.setDescription("description1");
        dto.setEmail("email1");
        dto.setImage(adImagesPath + "1");
        dto.setPhone("111");
        dto.setPrice(111);
        dto.setTitle("title1");
        return dto;
    }

    public static Author getAuthor1() {
        Author author = new Author();
        author.setId(1);
        author.setEmail("email1");
        author.setPassword("password");
        author.setFirstName("firstname1");
        author.setLastName("lastname1");
        author.setPhone("111");
        author.setRole(Role.USER);
        return author;
    }

    public static UserDto getUserDto1() {
        UserDto dto = new UserDto();
        dto.setId(1);
        dto.setEmail("email1");
        dto.setFirstName("firstname1");
        dto.setLastName("lastname1");
        dto.setPhone("111");
        dto.setRole(Role.USER);
        dto.setImage(avatarPath + "1");
        return dto;
    }

    public static UpdateUserDto getUpdateUserDto1() {
        UpdateUserDto dto = new UpdateUserDto();
        dto.setPhone("111");
        dto.setFirstName("firstname1");
        dto.setLastName("lastname1");
        return dto;
    }

    public static RegisterDto getRegisterDto1() {
        RegisterDto dto = new RegisterDto();
        dto.setUsername("email1");
        dto.setPassword("password");
        dto.setFirstName("firstname1");
        dto.setLastName("lastname1");
        dto.setPhone("111");
        dto.setRole(Role.USER);
        return dto;
    }

    public static CreateOrUpdateCommentDto getCreateOrUpdateCommentDto1(){
        CreateOrUpdateCommentDto dto = new CreateOrUpdateCommentDto();
        dto.setText("text1");
        return dto;
    }

    public static Comment getComment1() {
        Comment comment = new Comment();
        Author author = getAuthor1();
        Ad ad = getAd1();
        comment.setId(1);
        comment.setAuthor(author);
        comment.setAd(ad);
        comment.setCreatedAt(1L);
        comment.setText("text1");
        return comment;
    }

    public static CommentDto getCommentDto1() {
        CommentDto dto = new CommentDto();
        dto.setAuthor(1);
        dto.setAuthorImage(avatarPath + "1");
        dto.setAuthorFirstName("firstname1");
        dto.setCreatedAt(1L);
        dto.setPk(1);
        dto.setText("text1");
        return dto;
    }
}
