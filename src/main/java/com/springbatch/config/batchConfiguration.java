package com.springbatch.config;

import com.springbatch.steps.ItemDescompressStep;
import com.springbatch.steps.ItemReaderStep;
import com.springbatch.steps.ItemWriterStep;
import com.springbatch.steps.ItemProcessorStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class batchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @JobScope
    public ItemDescompressStep itemDescompressStep() {
        return new ItemDescompressStep();
    }

    @Bean
    @JobScope
    public ItemReaderStep itemReaderStep(){
        return new ItemReaderStep();
    }

    @Bean
    @JobScope
    public ItemProcessorStep itempProcessorStep(){
        return new ItemProcessorStep();
    }


    @Bean
    @JobScope
    public ItemWriterStep itemWriterStep(){
        return  new ItemWriterStep();
    }

    @Bean
    public Step descompressStep(){
        return stepBuilderFactory.get("descompressStep")
                .tasklet(itemDescompressStep())
                .build();
    }
    @Bean
    public Step readPersonStep() {
        return stepBuilderFactory.get("readPersonStep")
                .tasklet(itemReaderStep())
                .build();
    }

    @Bean
    public Step processPersonStep(){
        return stepBuilderFactory.get("processStep")
                .tasklet(itempProcessorStep())
                .build();
    }

    @Bean
    public Step writePersonStep() {
        return stepBuilderFactory.get("writeStep")
                .tasklet(itemWriterStep())
                .build();
    }

    @Bean
    public Job executeJob() {
        return jobBuilderFactory.get("execute")
                .incrementer(new RunIdIncrementer())
                .start(descompressStep())
                .next(readPersonStep())
                .next(processPersonStep())
                .next(writePersonStep())
                .build();
    }

}
