package com.zoo.stockbatch.job;

import com.zoo.stockbatch.StockFieldSetMapper;
import com.zoo.stockcommon.domain.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StockItemReaderJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    private static final int chunkSize = 10;

//    @Bean
//    public Job consoleWriterJob(){
//        return jobBuilderFactory.get("consoleWriterJob")
//                .start(consoleWriterStep())
//                .build();
//    }

    @Bean
    public Job pgWriterJob(@Value("${input.file.name}") String filename){
        return jobBuilderFactory.get("pgWriterJob")
                .start(pgWriterStep(filename))
                .build();
    }

//    @Bean
//    public Step consoleWriterStep(){
//        return stepBuilderFactory.get("consoleWriterStep")
//                .<Stock, Stock>chunk(chunkSize)
//                .reader(flatFileItemReader())
//                .writer(stockWriter())
//                .build();
//    }

    @Bean
    public Step pgWriterStep(@Value("${input.file.name}") String filename){
        return stepBuilderFactory.get("pgWriterStep")
                .<Stock, Stock>chunk(chunkSize)
                .reader(flatFileItemReader(filename))
                .writer(jpaItemWriter())
                .build();
    }

//    @Bean
//    public Job jdbcBatchItemWriterJob(){
//        return jobBuilderFactory.get("jdbcBatchItemWriterJob")
//                .start(jdbcBatchItemWriterStep())
//                .build();
//    }

//    @Bean
//    public Step jdbcBatchItemWriterStep(){
//        return stepBuilderFactory.get("jdbcBatchItemWriterStep")
//                .<Stock, Stock>chunk(chunkSize)
//                .reader(flatFileItemReader())
//                .writer(jdbcBatchItemWriter())
//                .build();
//    }

//    @Bean
//    public JdbcBatchItemWriter<Stock> jdbcBatchItemWriter(){
//        return new JdbcBatchItemWriterBuilder<Stock>()
//                .dataSource(dataSource)
//                .sql("insert into daily_price(code, date, price) values (:code, :date, :price)")
//                .beanMapped()
//                .build();
//    }

    @Bean
    public FlatFileItemReader<Stock> flatFileItemReader(@Value("${input.file.name}") String filename){

        FlatFileItemReader<Stock> itemReader = new FlatFileItemReader<Stock>();
        itemReader.setResource(new FileSystemResource(filename));

        //Set number of lines to skips. Use it if file has header rows.
        itemReader.setLinesToSkip(1);

        DefaultLineMapper<Stock> lineMapper = new DefaultLineMapper<Stock>();

        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new StockFieldSetMapper());
        itemReader.setLineMapper(lineMapper);

        return itemReader;
    }
//
//    private ItemWriter<Stock> stockWriter(){
//        return list -> {
//            for (Stock stock: list){
//                log.info("Current Stock={}", stock);
//            }
//        };
//    }

    @Bean
    public JpaItemWriter<Stock> jpaItemWriter(){
        JpaItemWriter<Stock> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

        return jpaItemWriter;
    }


}

