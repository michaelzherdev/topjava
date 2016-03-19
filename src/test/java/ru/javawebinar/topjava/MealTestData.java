package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;

    public static final UserMeal USER_MEAL1 = new UserMeal(MEAL1_ID, LocalDateTime.of(2016, 03, 19, 10, 00, 00), "Breakfast", 500);
    public static final UserMeal USER_MEAL2 = new UserMeal(MEAL1_ID+1, LocalDateTime.of(2016, 03, 19, 14, 00, 00), "Dinner", 800);
    public static final UserMeal USER_MEAL3 = new UserMeal(MEAL1_ID+2, LocalDateTime.of(2016, 03, 19, 19, 00, 00), "Lunch", 600);
    public static final UserMeal USER_MEAL4 = new UserMeal(MEAL1_ID+3, LocalDateTime.of(2016, 03, 20, 10, 00, 00), "Breakfast", 600);

    public static final UserMeal ADMIN_MEAL = new UserMeal(MEAL1_ID+4, LocalDateTime.of(2016, 03, 19, 12, 00, 00), "Admin Breakfast", 500);

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

}
