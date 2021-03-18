package com.zoo.stockbatch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class StockFieldSetMapper implements FieldSetMapper<Stock> {
    public Stock mapFieldSet(FieldSet fieldSet){
        Stock stock = new Stock();

        stock.setDate(fieldSet.readString(0));
        stock.setPrice(fieldSet.readString(1));

        return stock;
    }

}

