package com.avejava.springlibrary.alternativelibrary.controllers;

import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import com.avejava.springlibrary.alternativelibrary.domain.GenreEntity;
import com.avejava.springlibrary.alternativelibrary.service.BookEntityService;
import com.avejava.springlibrary.alternativelibrary.service.GenreEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainPageController {
    /** Инстансы сервисов */
    BookEntityService bookService;
    GenreEntityService genreService;

    List<BookEntity> topBooks;

    /** Содержимое библиотеки */
    static int pageNumber;      // номер текущей страницы (начиная с 1)
    static int pageSize;        // кол-во книг на одной странице
    static int maxPageNumber;   // сколько всего страниц
    static long totalElements;   // сколько всего элементов (на всех страницах)

    public MainPageController(BookEntityService bookService, GenreEntityService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;

        topBooks = bookService.findTopBooks(5);
        pageNumber = 1;
        pageSize = 10;
    }

    @GetMapping("/")
    public String baseUrlRedirect(Model model) {
        // Содержимое полки
        model.addAttribute("top", topBooks);
        // Содержимое меню жанров
        List<GenreEntity> genres = genreService.getAll();
        model.addAttribute("allGenres", genres);
        // Содержимое библиотеки, которое надо отобразить
        Page<BookEntity> pageBooks = bookService.getAll(pageNumber - 1, pageSize, "viewCount", Sort.Direction.ASC);
        model.addAttribute("pageBooks", pageBooks);
        // Информация о том, что отображаем (сколько страниц, номер текущей страницы, количество элементов на одной странице и общее количество элементов)
        maxPageNumber = pageBooks.getTotalPages();
        totalElements = pageBooks.getTotalElements();
        model.addAttribute("maxPage", MainPageController.maxPageNumber);
        model.addAttribute("thisPage", MainPageController.pageNumber);
        model.addAttribute("pageSize", MainPageController.pageSize);
        model.addAttribute("totalElements", MainPageController.totalElements);

        return "pages/main";
    }
}
