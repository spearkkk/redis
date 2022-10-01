package dev.spearkkk.redis.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@EqualsAndHashCode(of = "id")
@Getter
@RedisHash("user")
public class User {
    private Long id;
    private String name;
}
