package dev.spearkkk.redis.service

import dev.spearkkk.redis.domain.User
import dev.spearkkk.redis.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@SpringBootTest
class UserServiceTest extends Specification {
    @Autowired
    UserRepository userRepository
    @Autowired
    RedisTemplate<?, ?> redisTemplate

    @Shared
    static GenericContainer redis = new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(6379)

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        redis.start()
        registry.add("spring.redis.host", () -> redis.getHost())
        registry.add("spring.redis.port", () -> redis.getMappedPort(6379).toString())
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

    def "RedisTemplate should set and get value."() {
        given:
        redisTemplate.opsForValue().set("hello", "world")

        when:
        def found = redisTemplate.opsForValue().get("hello")

        then:
        true
        "world" == found
    }
}
