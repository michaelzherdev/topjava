package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.GET, query = "SELECT u FROM UserMeal u WHERE u.id=:id AND u.user.id=:userId"),
        @NamedQuery(name = UserMeal.GET_BETWEEN, query = "SELECT u FROM UserMeal u WHERE u.user.id=:userId" +
                " AND u.dateTime>= :after AND u.dateTime < :before ORDER BY u.dateTime DESC"),
        @NamedQuery(name = UserMeal.GET_SORTED, query = "SELECT u FROM UserMeal u WHERE u.user.id=:userId ORDER BY u.dateTime DESC"),
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal u WHERE u.user.id=:userId AND u.id=:id"),
        @NamedQuery(name = UserMeal.DELETE_ALL, query = "DELETE FROM UserMeal u WHERE u.user.id=:userId"),
})
@Entity
@Table(name = "meals")
public class UserMeal extends BaseEntity {

    public static final String GET = "UserMeal.get";
    public static final String GET_BETWEEN = "UserMeal.getBetween";
    public static final String GET_SORTED = "UserMeal.getSorted";
    public static final String DELETE = "UserMeal.delete";
    public static final String DELETE_ALL = "UserMeal.deleteAll";

    @Column(name = "date_time", nullable = false)
    @NotEmpty
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotEmpty
    private String description;

    @Column(name = "calories")
    @NotEmpty
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
