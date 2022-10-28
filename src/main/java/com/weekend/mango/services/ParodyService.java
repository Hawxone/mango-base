package com.weekend.mango.services;

import com.weekend.mango.models.Parody;

import java.util.List;

public interface ParodyService {
    List<Parody> getParodies();

    Parody saveParody(Parody parodyModel) throws Exception;

    Parody updateParody(Long id, Parody parodyModel) throws Exception;

    boolean deleteParody(Long id) throws Exception;
}
