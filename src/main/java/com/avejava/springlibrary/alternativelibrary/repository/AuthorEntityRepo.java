package com.avejava.springlibrary.alternativelibrary.repository;

import com.avejava.springlibrary.alternativelibrary.domain.AuthorEntity;
import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorEntityRepo extends JpaRepository<AuthorEntity, Long> {

    // найти авторов, значение поля fio которых содержит (в любом месте (начале, конце, середине))
    // переданную строку игнорируя раскладку, результаты отсортировать по значению поля fio
    List<AuthorEntity> findByFioContainingIgnoreCaseOrderByFio(String fio);

    // Page содержит некоторое количество результатов запроса
    // Pageable - параметры постраничности (сколько результатов выводит на одной странице и т.д.)
    Page<AuthorEntity> findByFioContainingIgnoreCaseOrderByFio(String fio, Pageable pageable);


}
