package se.svennesson.authserver;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.flywaydb.core.Flyway;
import org.skife.jdbi.v2.DBI;
import se.svennesson.authserver.auth.AuthServerAuthenticator;
import se.svennesson.authserver.config.AuthServerConfiguration;
import se.svennesson.authserver.dao.LoginAttemptDAO;
import se.svennesson.authserver.dao.AccessTokenDao;
import se.svennesson.authserver.dao.UserDAO;
import se.svennesson.authserver.models.User;
import se.svennesson.authserver.resources.UserResource;
import se.svennesson.authserver.services.AccessTokenService;
import se.svennesson.authserver.services.LoginAttemptsService;
import se.svennesson.authserver.services.UserService;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

public class AuthServerApplication extends Application<AuthServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new AuthServerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AuthServerConfiguration> bootstrap) {
        bootstrap.addBundle(new DBIExceptionsBundle());
    }

    @Override
    public void run(AuthServerConfiguration authServerConfiguration, Environment environment) throws Exception {

        // Flyway
        final DataSourceFactory dataSourceFactory = authServerConfiguration.getDataSourceFactory();
        final Flyway flyway = new Flyway();
        flyway.setDataSource(dataSourceFactory.getUrl(), dataSourceFactory.getUser(), dataSourceFactory.getPassword());
        flyway.migrate();

        //CORS
        final FilterRegistration.Dynamic corsFilter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
        corsFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
        corsFilter.setInitParameter(ALLOWED_METHODS_PARAM, "GET, PUT, POST, OPTIONS, DELETE, HEAD");
        corsFilter.setInitParameter(ALLOWED_ORIGINS_PARAM, "*");
        corsFilter.setInitParameter(ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization");
        corsFilter.setInitParameter(ALLOW_CREDENTIALS_PARAM, "true");

        // DBI
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, dataSourceFactory, "postgresql");

        // DAO
        final LoginAttemptDAO loginAttemptDAO = jdbi.onDemand(LoginAttemptDAO.class);
        final AccessTokenDao tokenDAO = jdbi.onDemand(AccessTokenDao.class);
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);

        // Services
        final AccessTokenService tokenService = new AccessTokenService(tokenDAO);
        final LoginAttemptsService loginAttemptsService = new LoginAttemptsService(loginAttemptDAO);
        final UserService userService = new UserService(userDAO, loginAttemptsService, tokenService);

        // Resources
        final UserResource userResource = new UserResource(userService, loginAttemptsService);

        // Auth
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new AuthServerAuthenticator(tokenService, userService))
                        .setPrefix("Bearer")
                        .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        // Jersey
        environment.jersey().register(userResource);
    }
}
