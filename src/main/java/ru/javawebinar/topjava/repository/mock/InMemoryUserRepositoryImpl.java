package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mikhail on 12.03.2016.
 */
public class InMemoryUserRepositoryImpl implements UserRepository {

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public static int ADMIN_ID = 1;
    public static int USER_ID = 2;

    {
        save(new User(ADMIN_ID, "Admin", "admin@admin.com", "password", Role.ROLE_ADMIN));
        save(new User(USER_ID, "User", "user@user.com", "password", Role.ROLE_USER));
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        User user = repository.get(id);
        if(user != null) {
            repository.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        for(Map.Entry<Integer, User> pair : repository.entrySet()) {
            if(pair.getValue().getEmail().equalsIgnoreCase(email))
                return pair.getValue();
        }
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return repository.values();
    }
}
