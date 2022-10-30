package com.weekend.mango.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="parodies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParodyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name ="manga_parody",
            joinColumns = @JoinColumn(name = "parody_id"),
            inverseJoinColumns = @JoinColumn(name = "manga_id"))
    List<MangaEntity> manga = new ArrayList<>();


}
