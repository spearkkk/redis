package dev.spearkkk.redis;

import dev.spearkkk.redis.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisApplicationTests {
	@Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {
		System.out.println("");
	}

}
