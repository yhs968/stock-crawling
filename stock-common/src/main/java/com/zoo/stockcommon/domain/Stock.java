package com.zoo.stockcommon.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
//@NoArgsConstructor
//@Table(name="daily_price")
@Entity
@IdClass(StockId.class)
public class Stock implements Serializable {

    @Id
    private String code;
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String date;
    private String price;

//    public String toString(){
//        return String.format(date+price+"");
//    }

}

