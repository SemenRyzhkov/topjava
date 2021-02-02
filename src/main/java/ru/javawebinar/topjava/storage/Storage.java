package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    List<Meal> getAllSorted();

    Meal getById(int id);

    void save(Meal meal);

    void update(Meal updatedMeal);

    void delete(int id);
}
