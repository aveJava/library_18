package com.avejava.springlibrary.alternativelibrary.repository;

import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookEntityRepo extends JpaRepository<BookEntity, Long> {

    // лист книг, для который найдено включение name в имени книги, или authorFio в имени автора книги
    // AuthorFioContaining... - поиск совпадения в поле fio объекта, находящегося в поле author кники
    List<BookEntity> findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(String name, String fio);

}
