package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.mealsUser1.forEach(meal -> save(meal, USER_ID));
        MealsUtil.mealsUser2.forEach(meal -> save(meal, ADMIN_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, userMeal -> new ConcurrentHashMap<>());
        return saveUserMeal(meal, userId, meals);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getUserMealSortedByDate(userId);
    }

    @Override
    public Collection<Meal> getAllFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getUserMealSortedByDate(userId)
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    private Meal saveUserMeal(Meal meal, int userId, Map<Integer, Meal> meals) {
        Meal savedMeal;
        if (meal.isNew()) {
            meal.setUserId(userId);
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            savedMeal = meal;
        } else {
            savedMeal = meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            if (savedMeal != null) {
                savedMeal.setUserId(userId);
            }
        }
        return savedMeal;
    }

    private Collection<Meal> getUserMealSortedByDate(int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? Collections.emptyList() :
                meals.values()
                        .stream()
                        .sorted((m1, m2) -> m2.getDate().compareTo(m1.getDate()))
                        .collect(Collectors.toList());
    }
}

