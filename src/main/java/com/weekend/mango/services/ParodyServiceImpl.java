package com.weekend.mango.services;

import com.weekend.mango.entities.ParodyEntity;
import com.weekend.mango.models.Parody;
import com.weekend.mango.repositories.ParodyEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ParodyServiceImpl implements ParodyService{

    private final ParodyEntityRepository parodyEntityRepository;

    public ParodyServiceImpl(ParodyEntityRepository parodyEntityRepository) {
        this.parodyEntityRepository = parodyEntityRepository;
    }

    @Override
    public List<Parody> getParodies() {

        List<ParodyEntity> parodyEntities = parodyEntityRepository.findAll();

        return parodyEntities.stream().map((parodyEntity) ->
                Parody.builder()
                        .id(parodyEntity.getId())
                        .name(parodyEntity.getName())
                        .build()
        ).toList();
    }

    @Override
    public Parody saveParody(Parody parodyModel) throws Exception {

        ParodyEntity parody = new ParodyEntity();

        try {
            parody.setName(parodyModel.getName());
            parodyEntityRepository.save(parody);
            parodyModel.setId(parody.getId());

        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

        return parodyModel;
    }

    @Override
    public Parody updateParody(Long id, Parody parodyModel) throws Exception {

        try {
            Optional<ParodyEntity> fetchParody = parodyEntityRepository.findById(id);

            fetchParody.ifPresent(parodyEntity -> {
                parodyEntity.setName(parodyModel.getName());
                parodyEntityRepository.save(parodyEntity);

                parodyModel.setName(parodyEntity.getName());
            });
        }catch (Exception e){
            throw new Exception("can't update entry " + e);
        }

        return parodyModel;
    }

    @Override
    public boolean deleteParody(Long id) throws Exception {

        try {
            Optional<ParodyEntity> fetchParody = parodyEntityRepository.findById(id);

            fetchParody.ifPresent(parodyEntityRepository::delete);

        }catch (Exception e){
            throw new Exception("can't delete entry " + e);
        }

        return true;
    }
}
