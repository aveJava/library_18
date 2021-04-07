package com.avejava.springlibrary.alternativelibrary.controllers;

import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import com.avejava.springlibrary.alternativelibrary.domain.GenreEntity;
import com.avejava.springlibrary.alternativelibrary.service.BookEntityService;
import com.avejava.springlibrary.alternativelibrary.service.GenreEntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainPageController {
    BookEntityService bookService;
    GenreEntityService genreService;

    public MainPageController(BookEntityService bookService, GenreEntityService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public String baseUrlRedirect(Model model) {
        List<BookEntity> topBooks = bookService.findTopBooks(5);
        model.addAttribute("top", topBooks);
        List<GenreEntity> genres = genreService.getAll();
        model.addAttribute("allGenres", genres);

        return "main/main";
    }

}
