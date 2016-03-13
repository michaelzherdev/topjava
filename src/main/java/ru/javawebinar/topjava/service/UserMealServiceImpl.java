package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        return repository.save(userMeal, userId);
    }

    @Override
    public void delete(int mealId, int userId) throws NotFoundException {
        repository.delete(mealId, userId);
    }

    @Override
    public UserMeal get(int mealId, int userId) throws NotFoundException {
        return repository.get(mealId, userId);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return ExceptionUtil.check(repository.getAll(userId), userId);
    }

    @Override
    public void update(UserMeal userMeal, int userId) {
        ExceptionUtil.check(repository.save(userMeal, userId), userMeal.getId());
    }

    @Override
    public Collection<UserMeal> getBetweenDate(int userId, LocalDateTime startTime, LocalDateTime endTime) {
        return repository.getBetween(startTime, endTime.plus(1, ChronoUnit.DAYS), userId);
    }
}
