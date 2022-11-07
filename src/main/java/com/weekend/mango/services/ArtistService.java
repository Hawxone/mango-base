package com.weekend.mango.services;

import com.weekend.mango.models.Artist;

import java.util.List;
import java.util.Map;

public interface ArtistService {
    List<Artist> getArtists();

    Artist saveArtist(Artist artistModel) throws Exception;

    Artist updateArtist(Long id, Artist artistModel) throws Exception;

    boolean deleteArtist(Long id) throws Exception;

    Map<String, Object> getPaginatedArtists(int page, int size);
}
