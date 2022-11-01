package com.weekend.mango.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    Long id;
    String comment;
    DateTime createdAt;
    UserModel user;
    Long mangaId;
}
