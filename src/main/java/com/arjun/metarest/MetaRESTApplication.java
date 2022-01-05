package com.arjun.metarest;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;

import com.arjun.metarest.Authentication.DAuthenticator;
import com.arjun.metarest.Authorization.DAuthorizer;
import com.arjun.metarest.domain.User;
import com.arjun.metarest.resource.MetaRESTHealthCheckResource;
import com.arjun.metarest.resource.EntityResource;
import com.arjun.metarest.service.EntityService;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;



public class MetaRESTApplication extends Application<MetaRESTConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(MetaRESTApplication.class);
	private static final String SQL = "sql";
	private static final String DROPWIZARD_MYSQL_SERVICE = "Dropwizard MySQL Service";

	public static void main(String[] args) throws Exception {
		new MetaRESTApplication().run("server", args[0]);
	}

    @Override
    public void initialize(Bootstrap<MetaRESTConfiguration> b) {
    }

	@Override
	public void run(MetaRESTConfiguration config, Environment env)
			throws Exception {
        // Datasource configuration
        final DataSource dataSource =
                config.getDataSourceFactory().build(env.metrics(), SQL);
        DBI dbi = new DBI(dataSource);

        // Register Health Check
        MetaRESTHealthCheckResource healthCheck =
                new MetaRESTHealthCheckResource(dbi.onDemand(EntityService.class));
        env.healthChecks().register(DROPWIZARD_MYSQL_SERVICE, healthCheck);
	    logger.info("Registering RESTful API resources");
        env.jersey().register(new EntityResource(dbi.onDemand(EntityService.class)));

        env.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                                .setAuthenticator(new DAuthenticator())
                                .setAuthorizer(new DAuthorizer())
                                .setRealm("BASIC-AUTH-REALM")
                                .buildAuthFilter()));
        env.jersey().register(RolesAllowedDynamicFeature.class);
        env.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

	}
}
