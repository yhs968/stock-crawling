package com.zoo.stockbatch;

import com.zoo.stockcommon.domain.Stock;
import com.zoo.stockcommon.repository.StockRepository;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableBatchProcessing

@SpringBootApplication
@EntityScan(basePackageClasses = {Stock.class})
@EnableJpaRepositories(basePackageClasses = {StockRepository.class})
public class StockBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockBatchApplication.class, args);
    }

}

