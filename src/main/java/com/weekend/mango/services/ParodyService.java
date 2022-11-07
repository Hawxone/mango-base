package com.weekend.mango.services;

import com.weekend.mango.models.Parody;

import java.util.List;
import java.util.Map;

public interface ParodyService {
    List<Parody> getParodies();

    Parody saveParody(Parody parodyModel) throws Exception;

    Parody updateParody(Long id, Parody parodyModel) throws Exception;

    boolean deleteParody(Long id) throws Exception;

    Map<String, Object> getPaginatedParodies(int page, int size);
}
