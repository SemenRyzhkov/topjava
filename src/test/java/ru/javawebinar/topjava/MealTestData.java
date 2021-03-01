package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int USER_MEAL_ID = START_SEQ;
    public static final int ADMIN_MEAL_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;

    public static final Meal userMeal = new Meal(
            100000, LocalDateTime.of(2021, Month.JANUARY, 22, 13, 21), "dinner", 1550);
    public static final Meal userMeal1 = new Meal(
            100001, LocalDateTime.of(2021, Month.JANUARY, 21, 19, 00), "dinner1", 1000);
    public static final Meal userMeal2 = new Meal(
            100002, LocalDateTime.of(2021, Month.JANUARY, 24, 00, 00), "dinner2", 1100);
    public static final Meal userMeal3 = new Meal(
            100003, LocalDateTime.of(2021, Month.JANUARY, 24, 12, 00), "dinner3", 1200);
    public static final Meal userMeal4 = new Meal(
            100004, LocalDateTime.of(2021, Month.JANUARY, 24, 14, 00), "dinner4", 1300);
    public static final Meal userMeal5 = new Meal(
            100005, LocalDateTime.of(2021, Month.JANUARY, 24, 19, 00), "dinner5", 1400);
    public static final Meal adminMeal = new Meal(
           100006, LocalDateTime.of(2021, Month.JANUARY, 21, 19, 40), "lunch", 1770);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2022, Month.JULY, 1, 12, 00), "new meal", 1200);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal);
        updated.setDateTime(LocalDateTime.of(2021, Month.FEBRUARY, 28, 13, 40));
        updated.setDescription("new description");
        updated.setCalories(1600);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
