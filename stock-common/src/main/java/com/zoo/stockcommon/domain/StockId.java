package com.zoo.stockcommon.domain;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class StockId implements Serializable {
    private String code;
    private String date;

    public StockId(String code, String date){
        this.code = code;
        this.date = date;
    }
}


