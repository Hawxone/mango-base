package com.weekend.mango.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="mangas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MangaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String title;

    private Integer mangaOrder;


    private DateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity createdBy;

    private DateTime uploadDate;

    @OneToMany(cascade= CascadeType.ALL,mappedBy = "manga",fetch = FetchType.LAZY)
    private List<MangaUserEntity> mangaUser = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL,mappedBy = "manga",fetch = FetchType.LAZY)
    private List<CommentEntity> comment = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL,mappedBy = "mangaId",fetch = FetchType.LAZY)
    private List<PageEntity> pageEntities = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name ="manga_artist",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<ArtistEntity> artist = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name ="manga_group",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<GroupEntity> group = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name ="manga_category",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> category = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name ="manga_parody",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "parody_id"))
    private List<ParodyEntity> parody = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name ="manga_character",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<CharacterEntity> character = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name ="manga_tag",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagEntity> tag = new ArrayList<>();



}
