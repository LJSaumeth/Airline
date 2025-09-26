package edu.unimag.domain.repositories;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
@DataJpaTest
public abstract class AbstractRepository {
	 @Container
	    @ServiceConnection
	    static PostgreSQLContainer<?> postgres = new   PostgreSQLContainer<>("postgres:16-alpine");
}
