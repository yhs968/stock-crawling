package com.batch.stockcrawling;

import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, String> {
}
