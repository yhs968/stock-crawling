package com.batch.stockcrawling;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor
//@Table(name="daily_price")
@Entity
public class Stock implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String date;
    private String price;

//    public String toString(){
//        return String.format(date+price+"");
//    }

}
