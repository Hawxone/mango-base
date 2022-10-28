package com.weekend.mango.repositories;

import com.weekend.mango.entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageEntityRepository extends JpaRepository<PageEntity,Long> {
}
