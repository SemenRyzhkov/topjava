package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsStorage {
    private static AtomicInteger mealCount= new AtomicInteger();
    private static final List<Meal> allMeals = new CopyOnWriteArrayList<>();
    private static final int CALORIES_PER_DAY = 2000;

    {
//       allMeals = new ArrayList<>();
       allMeals.add(new Meal(mealCount.incrementAndGet(),
               LocalDateTime.of(2021, Month.JANUARY, 1, 9, 30),
               "Завтрак",
               1000));
       allMeals.add(new Meal(mealCount.incrementAndGet(),
               LocalDateTime.of(2021, Month.JANUARY, 1, 12, 30),
               "Обед",
               2000));
       allMeals.add(new Meal(mealCount.incrementAndGet(),
               LocalDateTime.of(2021, Month.JANUARY, 1, 19, 0),
               "Ужин",
               1500));
       allMeals.add(new Meal(mealCount.incrementAndGet(),
               LocalDateTime.of(2021, Month.JANUARY, 2, 20, 0),
               "Ужин",
               1500));
    }

    public int getCALORIES_PER_DAY() {
        return CALORIES_PER_DAY;
    }

    public List<Meal> showAllMeals(){
        Collections.sort(allMeals);
        return allMeals;
    }

    public Meal getMealById(int id){
        return  allMeals.stream().filter(meal->meal.getId() == id).findAny().orElse(null);
    }

    public void saveMeal(Meal meal){
        meal.setId(mealCount.incrementAndGet());
        allMeals.add(meal);
    }

    public void updateMeal(Meal updatedMeal){
        Meal mealToBeUpdate = getMealById(updatedMeal.getId());
        mealToBeUpdate.setDateTime(updatedMeal.getDateTime());
        mealToBeUpdate.setDescription(updatedMeal.getDescription());
        mealToBeUpdate.setCalories(updatedMeal.getCalories());
    }

    public void deleteMeal(int id){
        allMeals.removeIf(meal -> meal.getId() == id);
    }
}
