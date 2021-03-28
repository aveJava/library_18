package com.avejava.springlibrary.alternativelibrary.repository;

import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookEntityRepo extends JpaRepository<BookEntity, Long> {

    // лист книг, для который найдено включение name в имени книги, или authorFio в имени автора книги
    // AuthorFioContaining... - поиск совпадения в поле fio объекта, находящегося в поле author кники
    List<BookEntity> findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(String name, String fio);

    // создает страницу (Page) книг (BookEntity) для которых заполнены все поля, кроме content
    @Query("select new BookEntity(b.id, b.name, b.pageCount, b.isbn, b.genre, b.author, b.publisher, b.publishYear, " +
            "b.image, b.avgRating, b.totalVoteCount, b.totalRating, b.viewCount, b.description) from BookEntity b")
    Page<BookEntity> findAllWithoutContent(Pageable pageable);  // возвращает список книг с постраничностью

    // обновляет книгу по id добавляя в нее контент
    @Modifying(clearAutomatically = true)
    @Query("update BookEntity b set b.content=:content where b.id=:id")     // :content - это ссылка на @Param("content")
    void updateContent(@Param("content") byte[] content, @Param("id") long id);

    // Для топовых книг показываем только изображение (в классе Book должен быть соответствующий конструктор)
    @Query("select new com.avejava.springlibrary.alternativelibrary.domain.BookEntity(b.id, b.image) from BookEntity b")
    List<BookEntity> findTopBooks(Pageable pageable);     // у книг будет заполнены только id и image

    // поиск книг по жанру
    @Query("select new BookEntity(b.id, b.name, b.pageCount, b.isbn, b.genre, b.author, b.publisher, b.publishYear, " +
            "b.image, b.avgRating, b.totalVoteCount, b.totalRating, b.viewCount, b.description) from BookEntity b " +
            "WHERE b.genre.id = :genreId")
    Page<BookEntity> findByGenre(@Param("genreId") int genreId, Pageable pageable);

    // получение контента по id
    @Query("SELECT b.content FROM BookEntity b WHERE b.id=:id")
    byte[] getContent(@Param("id") long id);
}
