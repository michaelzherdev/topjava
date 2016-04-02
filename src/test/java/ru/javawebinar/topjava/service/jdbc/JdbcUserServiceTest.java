package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

/**
 * Created by Mikhail on 01.04.2016.
 */

@ActiveProfiles({Profiles.POSTGRES, Profiles.JDBC})
public class JdbcUserServiceTest extends UserServiceTest {
}
