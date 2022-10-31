package com.weekend.mango.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTag {

    Long id;
    Long userId;
    Long tagId;
    String tagName;
    Boolean isFavorite;
}
