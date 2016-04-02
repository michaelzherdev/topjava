package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mikhail on 01.04.2016.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserMeal um WHERE um.user.id=:userId")
    void deleteAll(@Param("userId") int userId);

    @Override
    UserMeal save(UserMeal s);

    @Query("SELECT um FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId")
    UserMeal get(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT um FROM UserMeal um WHERE um.user.id=:userId")
    List<UserMeal> getAll(@Param("userId") int userId);

    @Query("SELECT um FROM UserMeal um WHERE um.user.id=:userId AND um.dateTime >= :start AND um.dateTime < :after ORDER BY um.dateTime DESC")
    List<UserMeal> getBetween(@Param("start")LocalDateTime startDate, @Param("after") LocalDateTime afterDate, @Param("userId") int userId);

}
