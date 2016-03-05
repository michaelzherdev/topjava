package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mikhail on 05.03.2016.
 */
public class MealDaoImpl implements MealDao{

    private Map<Integer, UserMeal> map = new HashMap<>();

    public MealDaoImpl() {
        int id = 0;
        add(new UserMeal(++id, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new UserMeal(++id, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new UserMeal(++id, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new UserMeal(++id, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new UserMeal(++id, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new UserMeal(++id, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public void add(UserMeal userMeal) {
        int id = 0;
        for(Map.Entry<Integer, UserMeal> pair : map.entrySet()) {
            if(pair.getKey() > id) id = pair.getKey();
        }
        map.put(userMeal.getId(), userMeal);
    }

    @Override
    public void edit(UserMeal userMeal) {
        int id = 0;
        for(Map.Entry<Integer, UserMeal> pair : map.entrySet()) {
            if(pair.getValue().equals(userMeal)) {
                id = pair.getKey();
            }
        }
        map.put(id, userMeal);
    }

    @Override
    public void delete(int userMealId) {
        map.remove(userMealId);
    }

    @Override
    public UserMeal getUserMeal(int userMealId) {
        return map.get(userMealId);
    }

    @Override
    public List<UserMeal> getAllUserMeals() {
        List<UserMeal> list = new ArrayList<>();
        for(Map.Entry<Integer, UserMeal> pair : map.entrySet()) {
            list.add(pair.getValue());
        }
        return list;
    }

    public Map<Integer, UserMeal> getMap() {
        return map;
    }

    public void setMap(Map<Integer, UserMeal> map) {
        this.map = map;
    }
}
