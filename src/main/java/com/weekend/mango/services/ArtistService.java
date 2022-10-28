package com.weekend.mango.services;

import com.weekend.mango.models.Artist;

import java.util.List;

public interface ArtistService {
    List<Artist> getArtists();

    Artist saveArtist(Artist artistModel) throws Exception;

    Artist updateArtist(Long id, Artist artistModel) throws Exception;

    boolean deleteArtist(Long id) throws Exception;
}
