package com.weekend.mango.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",
uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    private String email;


    private String imageUrl;

    @NotBlank
    @Size(max = 120)
    private String password;

    @OneToMany(cascade= CascadeType.ALL,mappedBy = "createdBy",fetch = FetchType.LAZY)
    private List<MangaEntity> mangaEntities = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL,mappedBy = "user",fetch = FetchType.LAZY)
    private List<MangaUserEntity> mangaUser = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL,mappedBy = "user",fetch = FetchType.LAZY)
    private List<CommentEntity> comment = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name ="user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles =new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name ="user_tag",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagEntity> tag = new ArrayList<>();



}
