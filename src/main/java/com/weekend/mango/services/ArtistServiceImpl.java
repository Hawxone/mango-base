package com.weekend.mango.services;

import com.weekend.mango.entities.ArtistEntity;
import com.weekend.mango.models.Artist;
import com.weekend.mango.repositories.ArtistEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService{

    private ArtistEntityRepository artistEntityRepository;

    public ArtistServiceImpl(ArtistEntityRepository artistEntityRepository) {
        this.artistEntityRepository = artistEntityRepository;
    }

    @Override
    public List<Artist> getArtists() {

        List<ArtistEntity> artistEntities = artistEntityRepository.findAll();

        return artistEntities.stream().map((artistEntity) ->
                Artist.builder()
                        .id(artistEntity.getId())
                        .name(artistEntity.getName())
                        .build()
        ).toList();
    }

    @Override
    public Artist saveArtist(Artist artistModel) throws Exception {

        ArtistEntity artistEntity = new ArtistEntity();

        try {
            artistEntity.setName(artistModel.getName());
            artistEntityRepository.save(artistEntity);

            artistModel.setId(artistEntity.getId());
        }catch (Exception e){
            throw new Exception("Could not save entry  " + e );
        }

        return artistModel;
    }

    @Override
    public Artist updateArtist(Long id, Artist artistModel) throws Exception {

        try {
            Optional<ArtistEntity> checkArtist = artistEntityRepository.findById(id);

            checkArtist.ifPresent(artistEntity -> {


                        artistEntity.setName(artistModel.getName());
                        artistEntityRepository.save(artistEntity);

                        artistModel.setName(artistEntity.getName());
                        artistModel.setId(artistEntity.getId());
                    }
            );

        }catch (Exception e){
            throw new Exception("cant update entry" + e);
        }

        return artistModel;
    }

    @Override
    public boolean deleteArtist(Long id) throws Exception {

        try {
            Optional<ArtistEntity> checkArtist = artistEntityRepository.findById(id);
            checkArtist.ifPresent(ArtistEntity->{
                artistEntityRepository.delete(ArtistEntity);
            });

        }catch (Exception e){
            throw new Exception("cant delete entry "+e);
        }

        return true;
    }
}
