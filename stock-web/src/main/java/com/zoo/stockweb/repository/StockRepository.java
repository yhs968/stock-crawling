package com.zoo.stockweb.repository;

import com.zoo.stockweb.domain.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, String> {
}
