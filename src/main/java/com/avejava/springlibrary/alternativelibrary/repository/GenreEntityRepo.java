package com.avejava.springlibrary.alternativelibrary.repository;

import com.avejava.springlibrary.alternativelibrary.domain.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreEntityRepo extends JpaRepository<GenreEntity, Long> {

    List<GenreEntity> findByNameContainingIgnoreCaseOrderByName(String name);

}
