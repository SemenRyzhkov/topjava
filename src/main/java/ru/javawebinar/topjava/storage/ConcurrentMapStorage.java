package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentMapStorage implements Storage {
    private AtomicInteger mealCount = new AtomicInteger();
    private final Map<Integer, Meal> allMeals = new ConcurrentHashMap<>();

    {
//       allMeals = new ArrayList<>();
        allMeals.put(mealCount.incrementAndGet(), new Meal(mealCount.get(),
                LocalDateTime.of(2021, Month.JANUARY, 1, 9, 30),
                "Завтрак",
                1000));
        allMeals.put(mealCount.incrementAndGet(), new Meal(mealCount.get(),
                LocalDateTime.of(2021, Month.JANUARY, 1, 12, 30),
                "Обед",
                2000));
        allMeals.put(mealCount.incrementAndGet(), new Meal(mealCount.get(),
                LocalDateTime.of(2021, Month.JANUARY, 1, 19, 0),
                "Ужин",
                1500));
        allMeals.put(mealCount.incrementAndGet(), new Meal(mealCount.get(),
                LocalDateTime.of(2021, Month.JANUARY, 2, 20, 0),
                "Ужин",
                1500));
    }

    public List<Meal> getAllSorted() {
        List<Meal> listOfMeal = new ArrayList<>(allMeals.values());
        listOfMeal.sort(Comparator.comparing(Meal::getDateTime));
        return listOfMeal;
    }

    public Meal getById(int id) {
        return allMeals.get(id);
    }

    public void save(Meal meal) {
        meal.setId(mealCount.incrementAndGet());
        allMeals.put(mealCount.get(), meal);
    }

    public void update(Meal updatedMeal) {
        allMeals.put(updatedMeal.getId(), updatedMeal);
    }

    public void delete(int id) {
        allMeals.remove(id);
    }
}
