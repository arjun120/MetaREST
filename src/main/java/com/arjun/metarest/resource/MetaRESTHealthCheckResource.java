package com.arjun.metarest.resource;

import com.arjun.metarest.service.EntityService;
import com.codahale.metrics.health.HealthCheck;

public class MetaRESTHealthCheckResource extends HealthCheck {

    private static final String HEALTHY_MESSAGE = "The Dropwizard blog Service is healthy for read and write";
    private static final String UNHEALTHY_MESSAGE = "The Dropwizard blog Service is not healthy. ";

    private final EntityService entityService;

    public MetaRESTHealthCheckResource(EntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    public Result check() throws Exception {
        String mySqlHealthStatus = entityService.performHealthCheck();

        if (mySqlHealthStatus == null) {
            return Result.healthy(HEALTHY_MESSAGE);
        } else {
            return Result.unhealthy(UNHEALTHY_MESSAGE , mySqlHealthStatus);
        }
    }
}