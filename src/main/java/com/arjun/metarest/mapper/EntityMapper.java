package com.arjun.metarest.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.arjun.metarest.domain.Entity;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;


public class EntityMapper implements ResultSetMapper<Entity> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DATASCHEMA = "dataSchema";
    private static final String DATA = "data";

    public Entity map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        Entity entity = new Entity(resultSet.getString(NAME), resultSet.getString(DATASCHEMA), resultSet.getString(DATA));
        entity.setId(resultSet.getInt(ID));
        return entity;
    }
}
