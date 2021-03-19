package com.zoo.stockweb.repository;

import com.zoo.stockweb.domain.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, String> { // serial은 4byte int type으로 보임
    @Query("select s from Stock s where s.code = ?1")
    List<Stock> findByCode(String code);
}
