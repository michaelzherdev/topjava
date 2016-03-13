package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */

@Controller
public class UserMealRestController {

    private static final Logger LOG = LoggerFactory.getLogger(UserMealRestController.class);

    @Autowired
    private UserMealService service;

    public UserMeal get(UserMeal userMeal) {
        int userId = LoggedUser.id();
        LOG.info("get meal for user {}", userId);
        return service.save(userMeal, userId);
    }

    public void delete(int mealId) throws NotFoundException {
        int userId = LoggedUser.id();
        LOG.info("delete meal for user {}", userId);
        service.delete(mealId, userId);
    }

    public Collection<UserMealWithExceed> getAll() {
        int userId = LoggedUser.id();
        LOG.info("getAll meal for user {}", userId);
        return UserMealsUtil.getFilteredWithExceeded(service.getAll(userId),
                LocalTime.MIN, LocalTime.MAX, LoggedUser.getCaloriesPerDay());
    }

    public void update(UserMeal userMeal) {
        int userId = LoggedUser.id();
        LOG.info("update meal for user {}", userId);
        service.save(userMeal, userId);
    }

    public void create(UserMeal userMeal) {
        int userId = LoggedUser.id();
        LOG.info("create meal for user {}", userId);
        service.save(userMeal, userId);
    }

    public List<UserMealWithExceed> getBetweenDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = LoggedUser.id();
        LOG.info("getBetweenDate meal for user {}", userId);
        return UserMealsUtil.getFilteredWithExceeded(service.getBetweenDate(userId, LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX)), startTime, endTime, LoggedUser.getCaloriesPerDay());
    }

}
