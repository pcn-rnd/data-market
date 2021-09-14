package co.kr.pcninc.data.marketcore.service;

import co.kr.pcninc.data.marketcore.domain.User;
import co.kr.pcninc.data.marketcore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> getUser(String userId) { return userRepository.findById(userId); }
}
