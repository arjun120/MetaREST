package com.arjun.metarest.service;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import com.arjun.metarest.dao.EntityDao;
import com.arjun.metarest.domain.Entity;

import java.util.List;
import java.util.Objects;

public abstract class EntityService {

    private static final Logger logger = LoggerFactory.getLogger(EntityService.class);

    private static final String DATABASE_ACCESS_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String UNEXPECTED_DATABASE_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success";
    private static final String UNEXPECTED_DELETE_ERROR = "An unexpected error occurred while deleting entity.";
    private static final String EMPLOYEE_NOT_FOUND = "Entity id %s not found.";

    @CreateSqlObject
    abstract EntityDao entityDao();

    public List<Entity> getEntities() {
        return entityDao().getEntities();
    }

    public Entity getEntity(int id) {
        Entity entity = entityDao().getEntity(id);
        if (Objects.isNull(entity)) {
            throw new WebApplicationException(String.format(EMPLOYEE_NOT_FOUND, id), Status.NOT_FOUND);
        }
        return entity;
    }

    public Entity createEntity(Entity entity) {
        entityDao().createEntity(entity);
        return entityDao().getEntity(entityDao().lastInsertId());
    }

    public Entity editEntity(Entity entity) {
        if (Objects.isNull(entityDao().getEntity(entity.getId()))) {
            throw new WebApplicationException(String.format(EMPLOYEE_NOT_FOUND, entity.getId()),
                    Status.NOT_FOUND);
        }
        entityDao().editEntity(entity);
        return entityDao().getEntity(entity.getId());
    }

    public String deleteEntity(final int id) {
        int result = entityDao().deleteEntity(id);
        logger.info("Result in EntityService.deleteEntity is: {}", result );
        switch (result) {
            case 1:
                return SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(EMPLOYEE_NOT_FOUND, id), Status.NOT_FOUND);
            default:
                throw new WebApplicationException(UNEXPECTED_DELETE_ERROR, Status.INTERNAL_SERVER_ERROR);
        }
    }

    public String performHealthCheck() {
        try {
            entityDao().getEntities();
        } catch (UnableToObtainConnectionException ex) {
            return checkUnableToObtainConnectionException(ex);
        } catch (UnableToExecuteStatementException ex) {
            return checkUnableToExecuteStatementException(ex);
        } catch (Exception ex) {
            return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
        }
        return null;
    }

    private String checkUnableToObtainConnectionException(UnableToObtainConnectionException ex) {
        if (ex.getCause() instanceof java.sql.SQLNonTransientConnectionException) {
            return DATABASE_ACCESS_ERROR + ex.getCause().getLocalizedMessage();
        } else if (ex.getCause() instanceof java.sql.SQLException) {
            return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
        }
    }

    private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
        if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
            return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
        }
    }
}
