package com.arjun.metarest.dao;
import com.arjun.metarest.domain.UserDetails;
import com.arjun.metarest.mapper.UserMapper;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UserMapper.class)
public interface UserDAO {
    @SqlQuery("select exists (select * from USERS where USERNAME = :username)")
    boolean exists(@Bind("username") String username);

    @SqlQuery("select PASSWORD from USERS where USERNAME = :username")
    String getPassword(@Bind("username") String username);

    @SqlUpdate("insert into USERS (USERNAME, password) values (:username,:password)")
    int insertUser(@BindBean UserDetails userDetails);
}
