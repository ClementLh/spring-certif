package com.clementlh.labs.springcore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AppConfigTest {

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "com.clementlh.labs.springcore")
    static class TestConfig {
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testTrainingService() {
        TrainingService trainingService = applicationContext.getBean(TrainingService.class);
        InMemoryTrainingRepository inMemoryTrainingRepository = applicationContext.getBean(InMemoryTrainingRepository.class);

        Object repository = ReflectionTestUtils.getField(trainingService, "repository");

        assertEquals(repository.getClass(), InMemoryTrainingRepository.class, "TrainingService should use InMemoryTrainingRepository");

        assertNotNull(trainingService);
        assertNotNull(inMemoryTrainingRepository);
    }

}
