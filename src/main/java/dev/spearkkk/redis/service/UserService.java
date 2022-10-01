package dev.spearkkk.redis.service;

import dev.spearkkk.redis.domain.User;
import dev.spearkkk.redis.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
