package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.validation.Validator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ru.javawebinar.topjava.util.ValidationUtil.validation;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

//    private static final BeanPropertyRowMapper<User> USER_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final Validator validator;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                              Validator validator) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.validator = validator;
    }


    @Override
    @Transactional
    public User save(User user) {
        validation(validator, user);

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            jdbcTemplate.batchUpdate("insert into user_roles values (?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(2, List.copyOf(user.getRoles()).get(i).name());
                    ps.setInt(1, user.getId());
                }

                @Override
                public int getBatchSize() {
                    return user.getRoles().size();
                }
            });


        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) != 0
        ) {
            jdbcTemplate.batchUpdate("update user_roles set role=? where user_id=?",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, List.copyOf(user.getRoles()).get(i).name());
                            ps.setInt(2, user.getId());
                        }

                        @Override
                        public int getBatchSize() {
                            return user.getRoles().size();
                        }
                    });
            return user;
        } else return null;
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("" +
                        "SELECT * FROM users u left outer join user_roles ur on u.id = ur.user_id WHERE u.id=?",
                new UserWithRoleExtractor(), id);
        return validation(validator, DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(@Email @NotBlank String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("" +
                        "SELECT * FROM users u left outer join user_roles ur on u.id = ur.user_id WHERE email=?",
                new UserWithRoleExtractor(), email);
        ;
        return validation(validator, DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("" +
                        "SELECT * FROM users LEFT JOIN user_roles ur on users.id = ur.user_id ORDER BY name, email",
                new UserWithRoleExtractor());
        Objects.requireNonNull(users).forEach(u -> ValidationUtil.validation(validator, u));
        return users;
    }

    private static final class UserWithRoleExtractor implements ResultSetExtractor<List<User>> {

        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User> usersMap = new HashMap<>();
            while (rs.next()) {
                Integer userId = rs.getInt("id");
                User user = usersMap.get(userId);
                if (user == null) {
                    user = new User();
                    user.setId(userId);
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setEnabled(rs.getBoolean("enabled"));
                    user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                    user.setRegistered(rs.getDate("registered"));
                }
                int albumUserId = rs.getInt("user_id");
                if (albumUserId > 0) {
                    Role role = Role.valueOf(rs.getString("role"));
                    user.addRole(role);
                }
                usersMap.put(userId, user);
            }
            return new ArrayList<>(usersMap.values());
        }
    }
}
