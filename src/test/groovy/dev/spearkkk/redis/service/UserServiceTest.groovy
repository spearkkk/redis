package dev.spearkkk.redis.service

import dev.spearkkk.redis.domain.User
import dev.spearkkk.redis.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@SpringBootTest
class UserServiceTest extends Specification {
    @Autowired
    UserRepository userRepository

    def setupSpec() {
        def redis =
                new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(6379)
        redis.start()
        System.setProperty("spring.redis.host", redis.getHost())
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString())
    }

    def "UserService should get user using id from redis."() {
        given:
        def userService = new UserService(userRepository)

        def user = new User(id: 1L, name: "test_user")
        def createdUser = userService.createUser(user)

        when:
        def gotUser = userService.getUser(createdUser.getId())

        then:
        gotUser == createdUser
    }
}
