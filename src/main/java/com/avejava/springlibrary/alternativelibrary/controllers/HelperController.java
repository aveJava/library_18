package com.avejava.springlibrary.alternativelibrary.controllers;

import com.avejava.springlibrary.alternativelibrary.domain.BookEntity;
import com.avejava.springlibrary.alternativelibrary.service.BookEntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HelperController {
    BookEntityService bookService;

    public HelperController(BookEntityService bookService) {
        this.bookService = bookService;
    }

    // Image-servlet - высылает изображение книги по ее id (id указывается в параметре id запроса)
    @GetMapping("/img")
    public String getImage(HttpServletResponse response, @RequestParam("id") int id, Model model) throws IOException {
        BookEntity book = bookService.get(id);

        byte[] imageBytes = book.getImage();
        response.setContentType("image/jpg");
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);

        return "";
    }

    // Toolbar_NumberButtons-servlet - слушает кнопки тулбара на главной странице
    @GetMapping("/Toolbar/NumberButtons")
    public String toolbarButtonListener(@RequestParam("title") String title) {
        switch (title) {
            case "<<":
                MainPageController.pageNumber = 1;
                break;
            case "<":
                if (MainPageController.pageNumber > 1) MainPageController.pageNumber--;
                break;
            case ">":
                MainPageController.pageNumber++;
                break;
            case ">>":
                MainPageController.pageNumber = MainPageController.maxPageNumber;
                break;
        }
        return "redirect:/";
    }

    // Toolbar_PageSize-servlet - слушает кнопки тулбара на главной странице
    @GetMapping("/Toolbar/PageSize")
    public String toolbarPageSizeListener(@RequestParam("size") int size) {
        switch (size) {
            case 5:
                MainPageController.pageSize = 5;
                break;
            case 10:
                MainPageController.pageSize = 10;
                break;
            case 15:
                MainPageController.pageSize = 15;
                break;
            case 20:
                MainPageController.pageSize = 20;
                break;
        }
        return "redirect:/";
    }

}
