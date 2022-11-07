package com.weekend.mango.services;

import com.weekend.mango.entities.ArtistEntity;
import com.weekend.mango.models.Artist;
import com.weekend.mango.models.PageIndex;
import com.weekend.mango.repositories.ArtistEntityRepository;
import com.weekend.mango.repositories.MangaEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArtistServiceImpl implements ArtistService{

    private final ArtistEntityRepository artistEntityRepository;
    private final MangaEntityRepository mangaEntityRepository;

    public ArtistServiceImpl(ArtistEntityRepository artistEntityRepository, MangaEntityRepository mangaEntityRepository) {
        this.artistEntityRepository = artistEntityRepository;
        this.mangaEntityRepository = mangaEntityRepository;
    }

    private long findFirst(Iterator<ArtistEntity> iterator, Long id) {

        long index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1;
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
    public Map<String, Object> getPaginatedArtists(int page, int size) {

        List<Artist> artistList = new ArrayList<>();
        Pageable paging = PageRequest.of(page,size).withSort(Sort.by("name").ascending());
        Page<ArtistEntity> artistEntities = artistEntityRepository.findAll(paging);
        Iterable<ArtistEntity> artistEntityIterable = artistEntityRepository.findAll(paging.getSort());

        List<PageIndex> pageIndices = new ArrayList<>();

        for(char alphabet = 'A'; alphabet <='Z'; alphabet++ )
        {
            ArtistEntity artistAlphabet = artistEntityRepository.findFirstByNameStartsWith(String.valueOf(alphabet));
            if (artistAlphabet != null){
                PageIndex pageIndex = new PageIndex();
                pageIndex.setName(String.valueOf(alphabet));
                pageIndex.setIndex(findFirst(artistEntityIterable.iterator(),artistAlphabet.getId()));
                pageIndices.add(pageIndex);
            }
        }

        for (ArtistEntity a: artistEntities
             ) {
            Long mangaCount = mangaEntityRepository.countMangaEntitiesByArtistId(a.getId());
            Artist artist = new Artist();
            artist.setId(a.getId());
            artist.setName(a.getName());
            artist.setMangaCount(mangaCount);
            artistList.add(artist);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("artistList",artistList);
        response.put("currentPage",artistEntities.getNumber());
        response.put("totalItems",artistEntities.getTotalElements());
        response.put("totalPages",artistEntities.getTotalPages());
        response.put("index",pageIndices);

        return response;
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
            checkArtist.ifPresent(artistEntityRepository::delete);

        }catch (Exception e){
            throw new Exception("cant delete entry "+e);
        }

        return true;
    }


}
