package com.weekend.mango.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MangaUser {
    Long id;
    Long MangaId;
    Long UserId;
    Boolean isWillRead;
    Boolean isFavorite;
    Integer currentPage;
}
