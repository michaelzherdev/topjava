package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.repository.DataBaseFiller;

/**
 * Created by Mikhail on 01.04.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
abstract public class DataBaseTest {

    @Rule
    ExpectedException thrown = ExpectedException.none();

    @Autowired
    DataBaseFiller filler;

    @Autowired
    UserService userService;

    @Before
    public void setUp() throws Exception{
        filler.execute();
        userService.evictCache();
    }
}


