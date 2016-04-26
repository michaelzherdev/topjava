package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Mikhail on 26.04.2016.
 */


@RestController
@RequestMapping("/ajax/profile/meals")
public class UserMealAjaxController extends AbstractUserMealController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAllWithExceed() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void update(@Valid UserMeal meal, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
        } else {
            status.setComplete();
            if (meal.getId() == 0) {
                super.create(meal);
            } else {
                super.update(meal, meal.getId());
            }
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal get(@PathVariable("id") int id) {
        return super.get(id);
    }


    public List<UserMealWithExceed> filterWithExceed(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime) {
        int caloriesPerDay = LoggedUser.getCaloriesPerDay();
        Map<LocalDate, Integer> groupAndSumMap = mealList.stream().collect(Collectors.groupingBy(
                userMeal -> userMeal.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)
        ));
        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(), groupAndSumMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}