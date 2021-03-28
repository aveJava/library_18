package com.avejava.springlibrary.alternativelibrary.repository;

import com.avejava.springlibrary.alternativelibrary.domain.PublisherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherEntityRepo extends JpaRepository<PublisherEntity, Long> {

    // поиск издателей по имени
    List<PublisherEntity> findByNameContainingIgnoreCaseOrderByName(String name);

    // поиск издателей по имени с постраничностью
    Page<PublisherEntity> findByNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);

}
