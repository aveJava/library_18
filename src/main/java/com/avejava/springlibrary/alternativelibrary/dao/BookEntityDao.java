package com.avejava.springlibrary.alternativelibrary.dao;

import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;

import java.util.List;

public interface BookEntityDao extends GeneralDao<BookEntity> {
    List<BookEntity> findTopBooks(int limit);
}
