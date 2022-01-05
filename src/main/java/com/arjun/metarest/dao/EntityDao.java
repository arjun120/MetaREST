package com.arjun.metarest.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

import com.arjun.metarest.domain.Entity;
import com.arjun.metarest.mapper.EntityMapper;

@RegisterMapper(EntityMapper.class)
public interface EntityDao {

    @SqlQuery("select * from entity;")
    public List<Entity> getEntities();

    @SqlQuery("select * from entity where id = :id")
    public Entity getEntity(@Bind("id") final int i);

    @SqlUpdate("insert into entity(name, dataSchema, data) values(:name, :dataSchema, :data)")
    void createEntity(@BindBean final Entity entity);

    @SqlUpdate("update entity set name = coalesce(:name, name), " +
            " dataSchema = coalesce(:dataSchema, dataSchema), data = coalesce(:data, data)" +
            " where id = :id")
    void editEntity(@BindBean final Entity entity);

    @SqlUpdate("delete from entity where id = :id")
    int deleteEntity(@Bind("id") final int id);

    @SqlQuery("select last_insert_id();")
    public int lastInsertId();
}

