package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    Comparator<UserMeal> comparator = new Comparator<UserMeal>() {
        @Override
        public int compare(UserMeal um1, UserMeal um2) {
            return um2.getDateTime().compareTo(um1.getDateTime());
        }
    };

    public InMemoryUserMealRepositoryImpl(){
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), InMemoryUserRepositoryImpl.USER_ID);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000), InMemoryUserRepositoryImpl.USER_ID);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), InMemoryUserRepositoryImpl.USER_ID);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), InMemoryUserRepositoryImpl.USER_ID);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), InMemoryUserRepositoryImpl.USER_ID);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), InMemoryUserRepositoryImpl.USER_ID);

        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "админский Завтрак", 1000), InMemoryUserRepositoryImpl.ADMIN_ID);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "админский Обед", 500), InMemoryUserRepositoryImpl.ADMIN_ID);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "админский Ужин", 510), InMemoryUserRepositoryImpl.ADMIN_ID);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        } else if (get(userMeal.getId(), userId) == null) {
            return null;
        }
        Map<Integer, UserMeal> meals = repository.get(userId);
        if(meals == null || meals.isEmpty()) {
            meals = new ConcurrentHashMap<>();
        }
        meals.put(userMeal.getId(), userMeal);
        repository.put(userId, meals);
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, UserMeal> meals = repository.get(userId);
        if(meals != null) {
            UserMeal userMeal = meals.remove(id);
            return userMeal != null ? true : false;
        }
        return false;
    }

    @Override
    public UserMeal get(int id, int userId) {
        Map<Integer, UserMeal> meals = repository.get(userId);
        return meals != null ? meals.get(id) : null;
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        Map<Integer, UserMeal> meals = repository.get(userId);
        System.out.println(meals.size());
        return meals.values().stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public Collection<UserMeal> getBetween(LocalDateTime startTime, LocalDateTime endTime, int userId) {
        return getAll(userId).stream()
                .filter(meal -> TimeUtil.isBetweenDateTime(meal.getDateTime(), startTime, endTime))
                .sorted(comparator).collect(Collectors.toList());
    }
}

