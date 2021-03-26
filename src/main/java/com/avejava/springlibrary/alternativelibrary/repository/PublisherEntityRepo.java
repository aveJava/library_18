package com.avejava.springlibrary.alternativelibrary.repository;

import com.avejava.springlibrary.alternativelibrary.domain.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherEntityRepo extends JpaRepository<PublisherEntity, Long> {

    List<PublisherEntity> findByNameContainingIgnoreCaseOrderByName(String name);

}
