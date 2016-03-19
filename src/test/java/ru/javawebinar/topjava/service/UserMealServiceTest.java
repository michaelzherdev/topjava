package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by Mikhail on 19.03.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(MEAL1_ID, START_SEQ);
        MATCHER.assertEquals(USER_MEAL1, userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(MEAL1_ID, START_SEQ + 1);
    }

    @Test
    public void testSave() throws Exception {
        UserMeal created = new UserMeal(null, LocalDateTime.of(2016, 03, 18, 10, 00, 00), "created Breakfast", 555);
        service.save(created, START_SEQ);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL1, USER_MEAL2, USER_MEAL3, USER_MEAL4, created), service.getAll(START_SEQ));
    }


    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL1_ID, START_SEQ);
        UserMeal[] array = {USER_MEAL2, USER_MEAL3, USER_MEAL4};
        MATCHER.assertCollectionEquals(Arrays.asList(array), service.getAll(START_SEQ));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL1, USER_MEAL2, USER_MEAL3, USER_MEAL4), service.getAll(START_SEQ));
    }
}
