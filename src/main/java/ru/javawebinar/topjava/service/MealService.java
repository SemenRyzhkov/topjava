package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        Meal meal = repository.get(id, userId);

        if (meal != null) {
            return checkNotFoundWithId(repository.get(id, userId), id);
        } else throw new NotFoundException("");
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Collection<Meal> getAllSortedByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllSortedByDate(userId, startDate, endDate);
    }
}