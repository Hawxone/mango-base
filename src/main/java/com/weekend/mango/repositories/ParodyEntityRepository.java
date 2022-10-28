package com.weekend.mango.repositories;

import com.weekend.mango.entities.ParodyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParodyEntityRepository extends JpaRepository<ParodyEntity,Long> {
}
