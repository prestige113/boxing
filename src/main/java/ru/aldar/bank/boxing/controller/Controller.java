package ru.aldar.bank.boxing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.aldar.bank.boxing.services.SearchService;

@RestController
@RequestMapping("/")
public class Controller {

    private
    SearchService searchService;

    @Autowired
    public Controller(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "get")
    public List<String> getItemsId(@RequestParam(name = "box") Integer box, @RequestParam(name = "color") String color) {
        return searchService.searchJdbc(box, color);
    }
}
