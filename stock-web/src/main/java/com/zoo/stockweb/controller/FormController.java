package com.zoo.stockweb.controller;

import com.zoo.stockweb.domain.Stock;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class FormController {

    @GetMapping("/stock")
    public String greetingForm(Model model) {
        model.addAttribute("stock", new Stock());
        return "stock";
    }

    @PostMapping("/stock")
    public String greetingSubmit(@ModelAttribute Stock stock, Model model) {
        model.addAttribute("stock", stock);
        return "result";
    }
}