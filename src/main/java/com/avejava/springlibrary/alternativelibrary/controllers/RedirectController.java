package com.avejava.springlibrary.alternativelibrary.controllers;

import com.avejava.springlibrary.alternativelibrary.domain.AuthorEntity;
import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import com.avejava.springlibrary.alternativelibrary.repository.AuthorEntityRepo;
import com.avejava.springlibrary.alternativelibrary.repository.BookEntityRepo;
import com.avejava.springlibrary.alternativelibrary.service.AuthorEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
public class RedirectController {
    private AuthorEntityRepo authorRepo;
    private BookEntityRepo bookRepo;
    private AuthorEntityService authorService;

    public RedirectController(AuthorEntityRepo authorRepo, BookEntityRepo bookRepo, AuthorEntityService authorService) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.authorService = authorService;
    }

    //@GetMapping("/")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("*********** Перешел на страртовую страницу ***********");
        List<AuthorEntity> authorsList = authorRepo.findAll();
        List<BookEntity> booksList = bookRepo.
                findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName("та", "ко");

        Sort sort = Sort.by(Sort.Direction.ASC, "fio");             // объект, инкапсулирующий параметры сортировки
        PageRequest pageRequest = PageRequest.of(0, 10, sort);      // объект, инкапсулирующий настройки постраничности
        Page<AuthorEntity> pageList = authorRepo.findByFioContainingIgnoreCaseOrderByFio("та", pageRequest);
        // pageList будет содержать авторов с 1 (т.к. page=0) по 10 (т.к. size=10) отсортированных по полю fio.
        // если бы параметр page был равен 1, тогда бы pageList содержал авторов с 11 по 20 (т.е. вторую страницу).

        Page<AuthorEntity> authors = authorService.getAll(0, 10, "fio", Sort.Direction.DESC);

        AuthorEntity author = new AuthorEntity();
        author.setBirthday(new Date(1453600000));
        AuthorEntity newAuthor = authorService.save(author);

        return "ok";
    }

}
