package se.svennesson.izettle;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.flywaydb.core.Flyway;
import org.skife.jdbi.v2.DBI;
import se.svennesson.izettle.auth.IzettleAuthenticator;
import se.svennesson.izettle.config.IzettleConfiguration;
import se.svennesson.izettle.dao.LoginAttemptDAO;
import se.svennesson.izettle.dao.AccessTokenDao;
import se.svennesson.izettle.dao.UserDAO;
import se.svennesson.izettle.models.User;
import se.svennesson.izettle.resources.UserResource;
import se.svennesson.izettle.services.AccessTokenService;
import se.svennesson.izettle.services.LoginAttemptsService;
import se.svennesson.izettle.services.UserService;

public class IzettleApplication extends Application<IzettleConfiguration> {

    public static void main(String[] args) throws Exception {
        new IzettleApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<IzettleConfiguration> bootstrap) {
        bootstrap.addBundle(new DBIExceptionsBundle());
    }

    @Override
    public void run(IzettleConfiguration izettleConfiguration, Environment environment) throws Exception {

        // Flyway
        final DataSourceFactory dataSourceFactory = izettleConfiguration.getDataSourceFactory();
        final Flyway flyway = new Flyway();
        flyway.setDataSource(dataSourceFactory.getUrl(), dataSourceFactory.getUser(), dataSourceFactory.getPassword());
        flyway.migrate();

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
                        .setAuthenticator(new IzettleAuthenticator(tokenService, userService))
                        .setPrefix("Bearer")
                        .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        // Jersey
        environment.jersey().register(userResource);
    }
}
