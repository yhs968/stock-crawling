package com.zoo.stockbatch;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> { // serial은 4byte int type으로 보임

    @Query("select s from Stock s where s.code = ?1")
    Stock findByCode(String code);
}
