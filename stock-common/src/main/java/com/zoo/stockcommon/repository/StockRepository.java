package com.zoo.stockcommon.repository;

import com.zoo.stockcommon.domain.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, Long> {
    @Query("select s from Stock s where s.code = ?1")
    List<Stock> findByCode(String code);
}
