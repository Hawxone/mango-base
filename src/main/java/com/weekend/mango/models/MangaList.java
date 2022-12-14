package com.weekend.mango.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MangaList {

    Long id;
    Integer orderId;
    String title;
    Long pageCount;
    String verdict;

}
