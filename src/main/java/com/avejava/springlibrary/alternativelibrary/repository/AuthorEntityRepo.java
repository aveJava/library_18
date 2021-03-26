package com.avejava.springlibrary.alternativelibrary.repository;

import com.avejava.springlibrary.alternativelibrary.domain.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorEntityRepo extends JpaRepository<AuthorEntity, Long> {

    // найти авторов, значение поля fio которых содержит (в любом месте (начале, конце, середине))
    // переданную строку игнорируя раскладку, результаты отсортировать по значению поля fio
    List<AuthorEntity> findByFioContainingIgnoreCaseOrderByFio(String fio);

}
