package com.zoo.stockweb.controller;

import com.zoo.stockweb.domain.Stock;
import com.zoo.stockweb.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FormController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/stock")
    public String greetingForm(Model model) {
        model.addAttribute("stock", new Stock());
        return "stock";
    }

    @PostMapping("/stock")
    public String greetingSubmit(@ModelAttribute Stock stock, Model model) {
        return String.format("redirect:/stock/%s", stock.getCode());
    }

    @GetMapping("/stock/{code}")
    @ResponseBody
    public String showStockData(@PathVariable("code") String code){
        List<Stock> stocks = stockRepository.findByCode(code);
        return stocks.toString();
    }
}