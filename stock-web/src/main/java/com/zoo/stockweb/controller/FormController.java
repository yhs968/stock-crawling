package com.zoo.stockweb.controller;

import com.zoo.stockcommon.domain.Stock;
import com.zoo.stockcommon.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public List<Stock> showStockData(@PathVariable("code") String code){
        return stockRepository.findByCode(code);
    }
}