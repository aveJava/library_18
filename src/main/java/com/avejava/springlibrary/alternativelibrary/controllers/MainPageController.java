package com.avejava.springlibrary.alternativelibrary.controllers;

import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import com.avejava.springlibrary.alternativelibrary.service.AuthorEntityService;
import com.avejava.springlibrary.alternativelibrary.service.BookEntityService;
import com.avejava.springlibrary.alternativelibrary.service.GenreEntityService;
import com.avejava.springlibrary.alternativelibrary.service.PublisherEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/** Контроллер, отвечающий за отображение главной страницы */
@Controller
public class MainPageController {
    /** Инстансы используемых сервисов */
    AuthorEntityService authorService;
    BookEntityService bookService;
    GenreEntityService genreService;
    PublisherEntityService publisherService;

    /** Состояние библиотеки */
    List<BookEntity> topBooks;      // топ книг (отображается на полке)
    Page<BookEntity> pageBooks;     // страница книг, которую нужно отобразить
    static int pageNumber;          // номер текущей страницы (начиная с 1)
    static int pageSize;            // кол-во книг на одной странице
    static int maxPageNumber;       // сколько всего страниц
    static long totalElements;      // сколько всего элементов (на всех страницах)
    // текущие критерии поиска
    SearchType searchType;          // тип используемого поиска
    long genreId;                   // id жанра (для поиска по жанру)
    String[] keywords;              // ключевые слова поиска (для поиска по keywords)

    public MainPageController(AuthorEntityService authorService, BookEntityService bookService,
                              GenreEntityService genreService, PublisherEntityService publisherService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
        this.publisherService = publisherService;

        // настройки первого посещения (отображаем все книги, первую страницу, 10 элементов на странице)
        pageNumber = 1;
        pageSize = 10;
        searchType = SearchType.ALL;
    }

    // отображает главную страницу согласно ее текущему состоянию
    @GetMapping("/")
    public String baseUrlRedirect(Model model) {
        // формирование отображаемого контента
        topBooks = bookService.findTopBooks(5);    // выполняет поиск топовых книг
        search();   // выполняет поиск книг, которые нужно отобразить в библиотеке

        // формирование модели, отправляемой на front
        model.addAttribute("top", topBooks);                            // Содержимое топовой полки
        model.addAttribute("allGenres", genreService.getAll());         // Содержимое меню жанров
        model.addAttribute("pageBooks", pageBooks);                     // Содержимое библиотеки (текущая страница)
        model.addAttribute("maxPage", MainPageController.maxPageNumber);
        model.addAttribute("thisPage", MainPageController.pageNumber);
        model.addAttribute("pageSize", MainPageController.pageSize);
        model.addAttribute("totalElements", MainPageController.totalElements);

        return "pages/main";
    }

    // слушает запросы на поиск книг (и меняет параметры поиска согласно полученному запросу)
    @GetMapping("/main_search")
    public String changeSearch(@RequestParam("type") String type,
                               @RequestParam(value = "keywords", required = false) String keywords,
                               @RequestParam(value = "genreId", required = false) Integer genreId) {
        pageNumber = 1;
        switch (type) {
            case ("all"):
                searchType = SearchType.ALL;
                break;
            case ("genre"):
                searchType = SearchType.SEARCH_GENRE;
                this.genreId = genreId;
                break;
        }

        return "redirect:/";
    }

    // выполняет поиск книг согласно текущим параметрам поиска, записывает результат в this.pageBooks
    public void search () {
        int pageNum = pageNumber - 1;    // контроллер страницы считает с 1, а Pageable с 0
        switch (searchType) {
            case ALL:
                pageBooks = bookService.getAll(pageNum, pageSize, "viewCount", Sort.Direction.ASC);
                break;
            case SEARCH_GENRE:
                pageBooks = bookService.findByGenre(pageNum, pageSize, "viewCount", Sort.Direction.DESC, genreId);
                break;
        }
        maxPageNumber = pageBooks.getTotalPages();
        totalElements = pageBooks.getTotalElements();
    }

}

enum SearchType {
    ALL,                // найти все книги
    SEARCH_GENRE,       // найти книги определенного жанра
}