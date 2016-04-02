package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

/**
 * Created by Mikhail on 01.04.2016.
 */
@ActiveProfiles({Profiles.POSTGRES, Profiles.JPA})
public class JpaUserMealServiceTest extends UserMealServiceTest{
}
