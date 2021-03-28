package com.avejava.springlibrary.alternativelibrary.dao;

import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BookEntityDao extends GeneralDao<BookEntity> {
    List<BookEntity> findTopBooks(int limit);
    byte[] getContent(long id);
    Page<BookEntity> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, long genreId);
}
