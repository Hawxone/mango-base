package com.weekend.mango.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name="pages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer pageOrder;
    private String file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manga_id")
    private MangaEntity mangaId;
}
