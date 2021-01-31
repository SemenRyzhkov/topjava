package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    public List<Meal> getAllSorted();

    public Meal getById(int id);

    public void save(Meal meal);

    public void update(Meal updatedMeal);

    public void delete(int id);
}
