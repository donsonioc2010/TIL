package com.jong1.controller;

import com.jong1.domain.item.Book;
import com.jong1.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /**
     * 상품 목록 조회
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "items/itemList";
    }

    /**
     * 상품 등록 페이지 이동
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    /**
     * 상품 등록
     */
    @PostMapping("/new")
    public String create(BookForm form) {
        itemService.saveItem(form.createEntity());
        return "redirect:/items";
    }
}
