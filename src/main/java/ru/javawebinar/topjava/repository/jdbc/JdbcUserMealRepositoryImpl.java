package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mikhail on 18.04.2016.
 */

@Repository
@Transactional(readOnly = true)
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final RowMapper<UserMeal> MAPPER = (rs, rowNum) -> new UserMeal(rs.getInt("id"),
            rs.getTimestamp("date_time").toLocalDateTime(), rs.getString("description"), rs.getInt("calories"));

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
        .withTableName("meals").usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories())
                .addValue("date_time", Timestamp.valueOf(userMeal.getDateTime()))
                .addValue("user_id", userId);

        if (userMeal.isNew()) {
            Number newKey = jdbcInsert.executeAndReturnKey(map);
            userMeal.setId(newKey.intValue());
        } else {
           if( namedParameterJdbcTemplate.update(
                    "UPDATE meals SET description=:description, calories=:calories, date_time=:date_time, " +
                            "WHERE id=:id AND user_id=:user_id", map) == 0)
               return null;
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id = ? AND user_id = ?", id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> list = jdbcTemplate.query("SELECT * FROM meals WHERE id = ? AND user_id = ?", MAPPER, id, userId);
        return DataAccessUtils.singleResult(list);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ? ORDER BY date_time DESC", MAPPER, userId);
    }

    @Override
    public Collection<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ? AND date_time >= ? AND date_time <= ?", MAPPER,
                userId, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
    }
}
