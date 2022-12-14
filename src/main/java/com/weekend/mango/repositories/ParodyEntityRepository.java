package com.weekend.mango.repositories;

import com.weekend.mango.entities.ParodyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface ParodyEntityRepository extends JpaRepository<ParodyEntity,Long> {

    ParodyEntity findFirstByNameStartsWith(@NotBlank @Size(max = 120) String name);

}
