package com.clementlh.labs.springcore;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Explicit Java configuration for the Spring Core lab.
 */
@Configuration
public class AppConfig {


    @Bean("trainingRepository")
    @Primary
    public TrainingRepository trainingRepository() {
        return new InMemoryTrainingRepository();
    }

    @Bean("trainingRepository")
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
