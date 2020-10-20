package com.akeijser.igtrader

import com.akeijser.igtrader.configuration.ApplicationConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension


/**
 * Abstract base class for writing slower unit tests that require a Spring context. Extend this class when you want the
 * autowire capabilities from Spring and the application.properties configuration of the application, and you need most
 * of the beans and/or a datasource.
 *
 *
 * When extending this class, you get a datasource, but no automatic tasks
 * (such as schedulers and monitors) are started. Thus, you can take full advantage of (almost) the entire application,
 * but the application itself will not start any tasks automatically.
 *
 *
 * For testing purposes the database is replaced with a new random database that is created with a random suffix on the fly.
 * Whenever a Spring context is created (which is whenever Spring decides), a new random database is created.
 * For transactional tests, clean the database before your test if you need to. Helper methods are available:
 * [.resetDatabase]}.
 *
 *
 * For details, see:
 *
 *
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html#testcontext-ctx-management-caching
 *
 *
 * For details, see "Writing tests" in README.md in the parent module.
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
abstract class AbstractFeatureTest {

    @Autowired
    protected final val config = ApplicationConfig()
}