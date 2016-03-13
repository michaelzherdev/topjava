package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 15.06.2015.
 */

public interface UserMealService {

    UserMeal save(UserMeal userMeal, int userId);

    void delete(int mealId, int userId) throws NotFoundException;

    UserMeal get(int mealId, int userId) throws NotFoundException;

    Collection<UserMeal> getAll(int userId);

    void update(UserMeal userMeal, int userId);

    Collection<UserMeal> getBetweenDate(int userId, LocalDateTime startTime, LocalDateTime endTime);
}
