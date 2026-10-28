package com.clementlh.labs.springcore;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Explicit Java configuration for the Spring Core lab.
 */
@Configuration
public class AppConfig {


    @Bean
    @Primary
    @ConditionalOnProperty(name = "training.repository.type", havingValue = "in-memory", matchIfMissing = true)
    public TrainingRepository trainingRepository() {
        return new InMemoryTrainingRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "training.repository.type", havingValue = "jpa")
    public TrainingRepository jpaTrainingRepository() {
        return new JpaTrainingRepository();
    }

    @Bean
    public TrainingService trainingService(TrainingRepository repository) {
        return new TrainingService(repository);
    }
    // TODO J6: declare configuration and bean methods.
    // TODO J6: decide which TrainingRepository implementation is injected.
}
