package com.arjun.metarest.mapper;
import com.arjun.metarest.domain.UserDetails;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<UserDetails> {

    public UserDetails map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new UserDetails(resultSet.getString("username"), resultSet.getString("password"));
    }
}