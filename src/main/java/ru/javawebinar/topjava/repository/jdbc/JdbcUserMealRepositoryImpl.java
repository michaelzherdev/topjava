package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final RowMapper<UserMeal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserMeal.class);

/*
    private static final RowMapper<UserMeal> ROW_MAPPER =
            (rs, rowNum) ->
                    new UserMeal(rs.getInt("id"), rs.getTimestamp("date_time").toLocalDateTime(),
                            rs.getString("description"), rs.getInt("calories"));
*/

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    private SimpleJdbcInsert insertUserMeal;
//
//    @Autowired
//    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
//        this.insertUserMeal = new SimpleJdbcInsert(dataSource)
//                .withTableName("meals")
//                .usingGeneratedKeyColumns("id");
//    }

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
//        MapSqlParameterSource map = new MapSqlParameterSource()
//                .addValue("id", userMeal.getId())
//                .addValue("description", userMeal.getDescription())
//                .addValue("calories", userMeal.getCalories())
//                .addValue("date_time", userMeal.getDateTime())
//                .addValue("user_id", userId);
//
//        if (userMeal.isNew()) {
//            Number newId = insertUserMeal.executeAndReturnKey(map);
//            userMeal.setId(newId.intValue());
//        } else {
//            if (namedParameterJdbcTemplate.update(
//                    "UPDATE meals SET description=:description, calories=:calories, date_time=:date_time " +
//                            " WHERE id=:id AND user_id=:user_id", map) == 0) {
//                return null;
//            }
//        }
        User user = em.getReference(User.class, userId);
        userMeal.setUser(user);
        if(userMeal.isNew()) {
            em.persist(userMeal);
        } else {
            if(get(userMeal.getId(), userId) == null)
                return null;
            em.merge(user);
        }

        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
//        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
        return em.createNamedQuery(UserMeal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Transactional
    public List<UserMeal> deleteAll(int userId) {
        return em.createNamedQuery(UserMeal.GET_SORTED)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public UserMeal get(int id, int userId) {
//        List<UserMeal> userMeals = jdbcTemplate.query(
//                "SELECT * FROM meals WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
//        return DataAccessUtils.singleResult(userMeals);
        List<UserMeal> userMeals =  em.createNamedQuery(UserMeal.GET, UserMeal.class)
                .setParameter("id", id)
                .setParameter("userId", userId).getResultList();
        return DataAccessUtils.singleResult(userMeals);
    }
    public List<UserMeal> getAll(int userId) {
//        return jdbcTemplate.query(
//                "SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
        return em.createNamedQuery(UserMeal.GET, UserMeal.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
//        return jdbcTemplate.query(
//                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
//                ROW_MAPPER, userId, startDate, endDate);
        return em.createNamedQuery(UserMeal.GET_BETWEEN)
                .setParameter("userId", userId)
                .setParameter("after", endDate)
                .setParameter("before", startDate).getResultList();
    }
}