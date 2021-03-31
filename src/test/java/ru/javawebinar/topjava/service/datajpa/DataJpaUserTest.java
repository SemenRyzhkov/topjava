package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User actual = service.getWitMeal(USER_ID);
        MEAL_MATCHER.assertMatch(actual.getMeals(), meals);
        USER_MATCHER.assertMatch(actual, user);
    }
}