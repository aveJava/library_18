package com.avejava.springlibrary.alternativelibrary.controller;

import com.avejava.springlibrary.alternativelibrary.domain.AuthorEntity;
import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import com.avejava.springlibrary.alternativelibrary.repository.AuthorEntityRepo;
import com.avejava.springlibrary.alternativelibrary.repository.BookEntityRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class RedirectController {
    private AuthorEntityRepo authorRepo;
    private BookEntityRepo bookRepo;

    public RedirectController(AuthorEntityRepo authorRepo, BookEntityRepo bookRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
    }

    @GetMapping
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse response) {
        List<AuthorEntity> authorsList = authorRepo.findAll();
        List<BookEntity> booksList = bookRepo.
                findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName("та", "ко");

        Sort sort = Sort.by(Sort.Direction.ASC, "fio");             // объект, инкапсулирующий параметры сортировки
        PageRequest pageRequest = PageRequest.of(0, 10, sort);      // объект, инкапсулирующий настройки постраничности
        Page<AuthorEntity> pageList = authorRepo.findByFioContainingIgnoreCaseOrderByFio("та", pageRequest);
        // pageList будет содержать авторов с 1 (т.к. page=0) по 10 (т.к. size=10) отсортированных по полю fio.
        // если бы параметр page был равен 1, тогда бы pageList содержал авторов с 11 по 20 (т.е. вторую страницу).

        return "ok";
    }

}
