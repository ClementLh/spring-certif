package com.clementlh.labs.springcore;

/**
 * Application service. Its repository dependency must be supplied externally.
 */
public class TrainingService {

    private final TrainingRepository repository;

    public TrainingService(TrainingRepository repository) {
        this.repository = repository;
    }

    public void findById(int id) {
        repository.findById(id);
    }
}
