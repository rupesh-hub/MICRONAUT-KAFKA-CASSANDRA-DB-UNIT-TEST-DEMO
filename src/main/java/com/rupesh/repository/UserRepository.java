package com.rupesh.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.rupesh.entity.UserEntity;
import com.rupesh.exception.GlobalException;
import com.rupesh.logging.CustomLogger;
import com.rupesh.mdel.UserResponse;
import com.rupesh.shared.BaseRepository;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Bean
public class UserRepository implements BaseRepository<UserEntity, String> {

    private static final CustomLogger LOGGER = CustomLogger.getInstance(UserRepository.class);
    private final CqlSession cqlSession;
    private static final String INSERT_QUERY = """
            INSERT INTO test_123
                        
            .users (id, first_name, last_name, email, username, password)
            VALUES (:id,:first_name,:last_name,:email,:username,:password);
            """;

    private static final String FETCH_USER_BY_EMAIL_OR_USERNAME_QUERY = """  
            SELECT id, first_name, last_name, email, username FROM test_123.users WHERE email = :username OR username = :username;
            """;
    private static final String FETCH_ALL_USER_QUERY = """  
            SELECT id, first_name, last_name, email, username FROM test_123.users;
            """;

    @Inject
    public UserRepository(CqlSession cqlSession) {
        this.cqlSession = cqlSession;
    }


    @Override
    public void save(final UserEntity data) {

        final Map<String, Object> map = new HashMap<>();
        map.put("id", data.getId());
        map.put("first_name", data.getFirstName());
        map.put("last_name", data.getLastName());
        map.put("email", data.getEmail());
        map.put("username", data.getUsername());
        map.put("password", data.getPassword());

        try {
            cqlSession.execute(INSERT_QUERY, map);
            LOGGER.log("DATA SUCCESSFULLY INSERTED !! ");
        } catch (final GlobalException e) {
            LOGGER.log("error occurs : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<UserEntity> findAll() {

        List<UserEntity> dataList = new ArrayList<>();

        try {
            final ResultSet rows = cqlSession.execute(FETCH_ALL_USER_QUERY);

            for (Row row : rows) {

                UserEntity entity = new UserEntity(row.getString("id"));
                entity.setFirstName(row.getString("first_name"));
                entity.setLastName(row.getString("last_name"));
                entity.setEmail(row.getString("email"));
                entity.setUsername(row.getString("username"));
                dataList.add(entity);

            }
        } catch (final GlobalException e) {
            e.printStackTrace();
            LOGGER.log(e.getMessage());
        }

        return dataList;
    }

    public UserResponse findByUsername(final String username) {
        final Map<String, Object> map = new HashMap<>();
        map.put("first_name", username);
        UserResponse userResponse = null;
        try {
            final ResultSet rows = cqlSession.execute(FETCH_USER_BY_EMAIL_OR_USERNAME_QUERY, username);

            for (Row row : rows) {
                String firstName = row.getString("first_name");
                String lastName = row.getString("last_name");
                String email = row.getString("email");

                userResponse = new UserResponse(firstName, lastName, email, username);

            }
        } catch (final GlobalException e) {
            e.printStackTrace();
            LOGGER.log(e.getMessage());
        }

        return userResponse;
    }


}
