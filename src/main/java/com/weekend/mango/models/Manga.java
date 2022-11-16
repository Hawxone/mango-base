package com.weekend.mango.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manga {


    Long id;
    String title;
    Integer mangaOrder;
    DateTime createdAt;
    UserModel createdBy;
    DateTime uploadDate;
    Long pageCount;
    MangaUser mangaUser;
    List<Artist> artist;
    List<Group> group;
    List<Category> category;
    List<Parody> parody;
    List<Character> character;
    List<Tag> tag;
    List<Comment> comment;

}
