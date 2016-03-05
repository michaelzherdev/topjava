package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * Created by Mikhail on 05.03.2016.
 */
public interface MealDao {

    public void add(UserMeal userMeal);

    public void edit(UserMeal userMeal);

    public void delete(int userMealId);

    public UserMeal getUserMeal(int userMealId);

    public List<UserMeal> getAllUserMeals();

}
