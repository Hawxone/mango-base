package com.weekend.mango.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="manga_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MangaUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isWillRead;
    private Boolean isFavorite;
    private Integer currentPage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manga_id")
    private MangaEntity manga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

}
